package com.bankingsystem.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bankingsystem.common.AuditContext;
import com.bankingsystem.dto.auth.AuthResponse;
import com.bankingsystem.dto.auth.LoginRequest;
import com.bankingsystem.dto.auth.RefreshTokenRequest;
import com.bankingsystem.dto.auth.RegisterRequest;
import com.bankingsystem.dto.auth.RegisterResponse;
import com.bankingsystem.dto.auth.TokenRefreshResponse;
import com.bankingsystem.dto.auth.UserProfileResponse;
import com.bankingsystem.entities.AppUser;
import com.bankingsystem.entities.Role;
import com.bankingsystem.events.audit.AuditEvent;
import com.bankingsystem.events.audit.AuditEventPublisher;
import com.bankingsystem.repositories.AppUserRepository;
import com.bankingsystem.repositories.RoleRepository;
import com.bankingsystem.security.AppUserPrincipal;
import com.bankingsystem.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuditEventPublisher auditEventPublisher;
    private final AuditContext auditContext;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El correo ya está registrado: " + request.email());
        }
        if (appUserRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El nombre de usuario ya está registrado: " + request.username());
        }

        String roleName = normalizeRoleName(request.role());
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Rol inválido: " + request.role()));

        AppUser appUser = new AppUser();
        appUser.setUsername(request.username());
        appUser.setEmail(request.email());
        appUser.setPassword(passwordEncoder.encode(request.password()));
        appUser.setEnabled(true);
        appUser.setRole(role);

        AppUser saved = appUserRepository.save(appUser);

        return new RegisterResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                ROLE_PREFIX + role.getName());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (DisabledException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El usuario está deshabilitado.");
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas.");
        }

        AppUserPrincipal principal = (AppUserPrincipal) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal);

        // RF-07.1: login exitoso genera auditoría.
        auditEventPublisher.publish(AuditEvent.of(
                principal.getId(),
                "LOGIN",
                "AppUser",
                principal.getId(),
                auditContext.resolveClientIp()));

        return new AuthResponse(accessToken, refreshToken, jwtService.getExpirationSeconds());
    }

    public TokenRefreshResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        boolean isUsableRefreshToken = jwtService.isTokenValid(refreshToken)
                && "refresh".equals(jwtService.extractTokenType(refreshToken));

        if (!isUsableRefreshToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token inválido o expirado.");
        }

        UUID userId = jwtService.extractUserId(refreshToken);
        AppUser appUser = appUserRepository.findByIdWithRole(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado."));
        AppUserPrincipal principal = new AppUserPrincipal(appUser);
        if (!principal.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El usuario está deshabilitado.");
        }

        String newAccessToken = jwtService.generateAccessToken(principal);
        return new TokenRefreshResponse(newAccessToken, jwtService.getExpirationSeconds());
    }

    public UserProfileResponse getProfile(AppUserPrincipal principal) {
        AppUser appUser = principal.getAppUser();
        return new UserProfileResponse(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getEmail(),
                ROLE_PREFIX + appUser.getRole().getName(),
                appUser.isEnabled());
    }

    private String normalizeRoleName(String rawRole) {
        return rawRole.startsWith(ROLE_PREFIX)
                ? rawRole.substring(ROLE_PREFIX.length())
                : rawRole;
    }
}