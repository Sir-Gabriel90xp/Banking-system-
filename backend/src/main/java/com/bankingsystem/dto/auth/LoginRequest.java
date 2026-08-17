package com.bankingsystem.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request de login (CU-02, RF-01.2). El login se hace por email, no por
 * username (ver 11_endpoints.md y comentario en AppUserPrincipal).
 */
public record LoginRequest(

        @NotBlank(message = "El correo es obligatorio.")
        @Email(message = "El correo no tiene un formato válido.")
        String email,

        @NotBlank(message = "La contraseña es obligatoria.")
        String password) {
}