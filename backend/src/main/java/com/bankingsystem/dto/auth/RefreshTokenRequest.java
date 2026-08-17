package com.bankingsystem.dto.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * Request de renovación de token (RF-01.4).
 * Ver 11_endpoints.md - POST /api/v1/auth/refresh.
 */
public record RefreshTokenRequest(

        @NotBlank(message = "El refresh token es obligatorio.")
        String refreshToken) {
}