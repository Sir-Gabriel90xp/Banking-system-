package com.bankingsystem.dto.auth;

/**
 * Respuesta de login (data de POST /api/v1/auth/login).
 * Ver 11_endpoints.md: { "token", "refreshToken", "expiresIn" }.
 */
public record AuthResponse(
        String token,
        String refreshToken,
        long expiresIn) {
}