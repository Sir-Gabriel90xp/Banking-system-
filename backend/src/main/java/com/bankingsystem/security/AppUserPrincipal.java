package com.bankingsystem.security;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bankingsystem.entities.AppUser;

/**
 * Adaptador entre {@link AppUser} (entidad JPA, sin dependencias de
 * Spring Security) y el contrato {@link UserDetails} que exige el
 * módulo de autenticación.
 *
 * Se optó por esta clase envoltorio en vez de que AppUser implemente
 * UserDetails directamente: mantiene la entidad libre de lógica de
 * framework de seguridad (02_architecture.md - Clean Code / SOLID -
 * Single Responsibility), y evita acoplar el modelo de datos a la
 * API de Spring Security.
 *
 * Los roles en la base de datos se guardan sin prefijo (ADMIN, EMPLOYEE,
 * CUSTOMER, ver seed en V1__init_schema.sql), pero Spring Security espera
 * el prefijo "ROLE_" para que hasRole()/@PreAuthorize("hasRole(...)")
 * funcionen correctamente. El prefijo se agrega aquí, no se guarda así
 * en la BD, para no acoplar el esquema de datos a una convención de
 * un framework específico.
 */
public class AppUserPrincipal implements UserDetails {

    private final AppUser appUser;

    public AppUserPrincipal(AppUser appUser) {
        this.appUser = appUser;
    }

    public UUID getId() {
        return appUser.getId();
    }

    public AppUser getAppUser() {
        return appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + appUser.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        // Login se hace por email (ver 11_endpoints.md, POST /auth/login),
        // por eso el "username" de Spring Security es el email, no
        // appUser.getUsername(). Ambos campos son independientes en el modelo.
        return appUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // No hay bloqueo de cuenta de usuario a nivel de login todavía
        // (distinto de bloqueo de Account bancaria, RF-03.3/CU-06).
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // RF-01.2 (CU-02): un usuario deshabilitado (enabled = false) no
        // puede iniciar sesión (403 Forbidden). Un usuario eliminado
        // lógicamente (soft delete) tampoco debe poder autenticarse.
        return appUser.isEnabled() && !appUser.isDeleted();
    }
}