package com.bankingsystem.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingsystem.entities.AppUser;

/**
 * Repositorio de {@link AppUser}.
 *
 * CU-02 (Inicio de Sesión) autentica por email (ver 11_endpoints.md,
 * POST /api/v1/auth/login: { "email", "password" }), por eso el método
 * principal de búsqueda es findByEmail. findByUsername se deja disponible
 * porque RF-01.1/CU-01 registra tanto username como email, y username
 * también debe ser único (RN-03 solo exige unicidad de email + documento,
 * pero el esquema ya declara username unique en V1__init_schema.sql).
 *
 * No se filtra explícitamente por "deleted" aquí: AppUser usa soft delete
 * (SoftDeletableEntity), pero la decisión de si un usuario eliminado
 * lógicamente puede o no autenticarse es una regla de negocio del Service,
 * no del Repository (ver 02_architecture.md: el Repository no contiene
 * reglas de negocio).
 */
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    /**
     * Variante de findById que trae el Role ya resuelto (JOIN FETCH) en
     * la misma consulta. Necesario en JwtAuthenticationFilter: ese filtro
     * no corre dentro de una transacción de Service, así que un Role
     * LAZY sin resolver revienta con LazyInitializationException al
     * llamar AppUserPrincipal.getAuthorities() fuera de sesión de
     * Hibernate (ver incidente Sesión 7 - runtime, login tenía el mismo
     * problema, resuelto ahí con @Transactional en AuthService.login).
     */
    @Query("SELECT u FROM AppUser u JOIN FETCH u.role WHERE u.id = :id")
    Optional<AppUser> findByIdWithRole(@Param("id") UUID id);
}