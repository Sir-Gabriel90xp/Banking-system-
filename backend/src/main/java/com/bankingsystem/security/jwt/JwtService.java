package com.bankingsystem.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bankingsystem.security.AppUserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Generación y validación de JWT (RF-01.3, RF-01.4).
 *
 * Usa la API de jjwt 0.12.6 (Jwts.builder()/Jwts.parser() con SecretKey),
 * confirmada en pom.xml. La configuración (jwt.secret, jwt.expiration-ms,
 * jwt.refresh-expiration-ms) ya existe en application.yml.
 *
 * El subject del token es el id (UUID) del AppUser, no el email: evita
 * tener que volver a resolver por email en cada request del filtro y es
 * estable aunque el email cambie en el futuro.
 *
 * Se incluye un claim "type" ("access" | "refresh") para que el filtro y
 * el endpoint de refresh puedan rechazar un token usado en el flujo
 * equivocado (ej. un refresh token presentado como Bearer de acceso).
 */
@Service
public class JwtService {

    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_TYPE = "type";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";

    private final SecretKey key;
    private final long expirationMs;
    private final long refreshExpirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs,
            @Value("${app.jwt.refresh-expiration-ms}") long refreshExpirationMs) {
        // HS256 requiere al menos 256 bits (32 bytes) de clave. El valor
        // por defecto en application.yml ("change-this-secret-in-production")
        // ya tiene 32 caracteres UTF-8; en producción debe ser un secreto
        // real de al menos 32 bytes.
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
    }

    public String generateAccessToken(AppUserPrincipal principal) {
        return buildToken(principal, expirationMs, TYPE_ACCESS);
    }

    public String generateRefreshToken(AppUserPrincipal principal) {
        return buildToken(principal, refreshExpirationMs, TYPE_REFRESH);
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(parseClaims(token).getSubject());
    }

    public String extractTokenType(String token) {
        return parseClaims(token).get(CLAIM_TYPE, String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public long getExpirationSeconds() {
        return expirationMs / 1000;
    }

    private String buildToken(AppUserPrincipal principal, long ttlMs, String tokenType) {
        Instant now = Instant.now();
        String role = principal.getAuthorities().iterator().next().getAuthority();

        return Jwts.builder()
                .subject(principal.getId().toString())
                .claim(CLAIM_EMAIL, principal.getUsername())
                .claim(CLAIM_ROLE, role)
                .claim(CLAIM_TYPE, tokenType)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(ttlMs)))
                .signWith(key)
                .compact();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}