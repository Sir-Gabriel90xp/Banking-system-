package com.bankingsystem.dto.auth;

import java.util.UUID;

/**
 * Respuesta de GET /api/v1/auth/me (11_endpoints.md): objeto User (DTO,
 * sin password). Se incluye como smoke-test natural de que el
 * JwtAuthenticationFilter protege endpoints correctamente; el resto del
 * módulo Users (CRUD completo) queda para su propia tarea.
 */
public record UserProfileResponse(
        UUID id,
        String username,
        String email,
        String role,
        boolean enabled) {
}