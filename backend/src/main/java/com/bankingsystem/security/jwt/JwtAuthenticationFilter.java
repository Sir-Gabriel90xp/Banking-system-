package com.bankingsystem.security.jwt;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bankingsystem.entities.AppUser;
import com.bankingsystem.repositories.AppUserRepository;
import com.bankingsystem.security.AppUserPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Filtro que puebla el SecurityContext a partir de un access token JWT
 * válido en el header Authorization (RF-01.6, tareas.md "Proteger
 * endpoints").
 *
 * Este es el filtro que AI_HANDOFF.md indica insertar en SecurityConfig,
 * en el punto ya marcado con TODO en el filter chain. Este archivo NO
 * modifica SecurityConfig directamente (no se compartió su contenido);
 * ver SECURITY_CONFIG_INTEGRATION.md para la integración exacta.
 *
 * Solo acepta tokens de tipo "access" (no permite usar un refresh token
 * como credencial de acceso a endpoints protegidos).
 *
 * Usa findByIdWithRole (JOIN FETCH) en vez de findById: este filtro no
 * corre dentro de una transacción de Service, así que un Role LAZY sin
 * resolver revienta con LazyInitializationException al llamar
 * AppUserPrincipal.getAuthorities() fuera de sesión de Hibernate (mismo
 * problema detectado y resuelto en AuthService.login, verificación
 * runtime de la Fase 2).
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String TOKEN_TYPE_ACCESS = "access";

    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        boolean isUsableAccessToken = jwtService.isTokenValid(token)
                && TOKEN_TYPE_ACCESS.equals(jwtService.extractTokenType(token));

        if (isUsableAccessToken && SecurityContextHolder.getContext().getAuthentication() == null) {
            UUID userId = jwtService.extractUserId(token);
            appUserRepository.findByIdWithRole(userId).ifPresent(appUser -> authenticate(appUser, request));
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(AppUser appUser, HttpServletRequest request) {
        AppUserPrincipal principal = new AppUserPrincipal(appUser);

        if (!principal.isEnabled()) {
            // Usuario deshabilitado o eliminado: no se autentica, la
            // request sigue como anónima y será rechazada por el filter
            // chain si el endpoint requiere autenticación.
            return;
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}