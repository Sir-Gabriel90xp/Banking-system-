package com.bankingsystem.dto.auth;

import java.util.UUID;

/**
 * Respuesta de registro (data de POST /api/v1/auth/register).
 * Ver 11_endpoints.md. Nunca expone la entidad AppUser (ADR-004).
 */
public record RegisterResponse(
        UUID id,
        String username,
        String email,
        String role) {
}