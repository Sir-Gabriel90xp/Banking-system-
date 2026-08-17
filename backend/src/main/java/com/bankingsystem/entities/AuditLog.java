package com.bankingsystem.entities;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Registro de auditoría (RF-07, RN-05).
 *
 * Mapea la tabla `audit_log` de V1__init_schema.sql.
 *
 * A diferencia de las demás entidades, esta NO extiende Auditable:
 * el esquema real de `audit_log` solo tiene `id` y `timestamp`, sin
 * created_at/updated_at/created_by/updated_by (tiene sentido: un
 * registro de auditoría no se actualiza ni necesita su propio auditor).
 *
 * `user` es opcional porque el propio SQL lo permite (puede ser NULL
 * si la acción fue 100% del sistema, sin un usuario detrás).
 */
@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "action", nullable = false, length = 100)
    private String action;

    @Column(name = "entity", nullable = false, length = 100)
    private String entity;

    @Column(name = "entity_id")
    private UUID entityId;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp = Instant.now();
}