package com.bankingsystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.auth.AuthResponse;
import com.bankingsystem.dto.auth.LoginRequest;
import com.bankingsystem.dto.auth.RefreshTokenRequest;
import com.bankingsystem.dto.auth.RegisterRequest;
import com.bankingsystem.dto.auth.RegisterResponse;
import com.bankingsystem.dto.auth.TokenRefreshResponse;
import com.bankingsystem.dto.auth.UserProfileResponse;
import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.security.AppUserPrincipal;
import com.bankingsystem.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller de Auth (11_endpoints.md, módulo 1). No contiene lógica de
 * negocio (02_architecture.md); delega todo a AuthService.
 *
 * Rutas públicas (deben coincidir con SecurityConfig): /register, /login,
 * /refresh. /me requiere JWT (JwtAuthenticationFilter).
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse data = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario registrado exitosamente.", data));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse data = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Inicio de sesión exitoso.", data));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {
        TokenRefreshResponse data = authService.refresh(request);
        return ResponseEntity.ok(ApiResponse.success("Token renovado exitosamente.", data));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> me(
            @AuthenticationPrincipal AppUserPrincipal principal) {
        UserProfileResponse data = authService.getProfile(principal);
        return ResponseEntity.ok(ApiResponse.success("Perfil obtenido exitosamente.", data));
    }
}