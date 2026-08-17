package com.bankingsystem.dto.auth;

/**
 * Respuesta de POST /api/v1/auth/refresh (11_endpoints.md).
 * A diferencia de AuthResponse, no incluye un nuevo refreshToken:
 * el diseño aprobado solo renueva el access token en este endpoint.
 */
public record TokenRefreshResponse(
        String token,
        long expiresIn) {
}