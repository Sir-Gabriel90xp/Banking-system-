package com.bankingsystem.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingsystem.entities.Role;

/**
 * Repositorio de {@link Role}.
 *
 * Necesario para AuthService.register(): el RegisterRequest llega con un
 * nombre de rol (ej. "ROLE_CUSTOMER", ver 11_endpoints.md) que debe
 * resolverse contra el seed de roles ya insertado en V1__init_schema.sql
 * (ADMIN, EMPLOYEE, CUSTOMER, guardados sin prefijo).
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);
}