package com.bankingsystem.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request de registro (CU-01, RF-01.1).
 * Ver 11_endpoints.md - POST /api/v1/auth/register.
 *
 * "role" llega con el prefijo "ROLE_" (ej. "ROLE_CUSTOMER") tal como lo
 * define el ejemplo de 11_endpoints.md, aunque en la tabla `role` de
 * PostgreSQL los nombres se guardan sin prefijo (ver AppUserPrincipal).
 * La normalización se hace en AuthService, no aquí.
 */
public record RegisterRequest(

        @NotBlank(message = "El nombre de usuario es obligatorio.")
        @Size(max = 100, message = "El nombre de usuario no puede superar los 100 caracteres.")
        String username,

        @NotBlank(message = "El correo es obligatorio.")
        @Email(message = "El correo no tiene un formato válido.")
        @Size(max = 150, message = "El correo no puede superar los 150 caracteres.")
        String email,

        @NotBlank(message = "La contraseña es obligatoria.")
        @Size(min = 6, max = 255, message = "La contraseña debe tener al menos 6 caracteres.")
        String password,

        @NotBlank(message = "El rol es obligatorio.")
        String role) {
}