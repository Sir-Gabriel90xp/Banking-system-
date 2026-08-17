package com.bankingsystem.events.audit;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Payload publicado a RabbitMQ (RF-07.3). No es la entidad AuditLog:
 * es un mensaje desacoplado que el consumidor traduce a AuditLog.
 * Serializable por convención de mensajería, aunque el converter real
 * usado es JSON (Jackson2JsonMessageConverter), no serialización Java.
 */
public record AuditEvent(
        UUID userId,
        String action,
        String entity,
        UUID entityId,
        String ipAddress,
        Instant timestamp
) implements Serializable {

    public static AuditEvent of(UUID userId, String action, String entity, UUID entityId, String ipAddress) {
        return new AuditEvent(userId, action, entity, entityId, ipAddress, Instant.now());
    }
}