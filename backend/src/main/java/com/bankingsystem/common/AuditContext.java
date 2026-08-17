package com.bankingsystem.common;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bankingsystem.security.AppUserPrincipal;

/**
 * DRY: extrae userId (desde Authentication) e IP (desde el request HTTP
 * actual) para eventos de auditoría. Usado por todos los Services que
 * publican AuditEvent, evitando duplicar esta lógica en cada uno.
 */
@Component
public class AuditContext {

    public UUID resolveUserId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AppUserPrincipal principal)) {
            return null;
        }
        return principal.getId();
    }

    public String resolveClientIp() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) {
            return null;
        }

        String forwardedFor = attrs.getRequest().getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        return attrs.getRequest().getRemoteAddr();
    }
}