package com.bankingsystem.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankingsystem.entities.AppUser;
import com.bankingsystem.repositories.AppUserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementación de {@link UserDetailsService} usada por el
 * AuthenticationManager de Spring Security para validar credenciales
 * (CU-02, RF-01.2).
 *
 * Se apoya en {@link AppUserRepository}, no accede a la base de datos
 * directamente (02_architecture.md - el Service no reemplaza al
 * Repository, pero esta clase vive en el paquete security por ser
 * infraestructura de autenticación, no lógica de negocio de dominio).
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe un usuario con el correo: " + email));
        return new AppUserPrincipal(appUser);
    }
}