package com.bankingsystem.entities.base;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase base para entidades que soportan eliminación lógica (RNF-07):
 * un flag "deleted" más el timestamp de cuándo ocurrió, en lugar de
 * eliminar físicamente la fila.
 *
 * Solo la extienden las entidades que en V1__init_schema.sql tienen las
 * columnas deleted/deleted_at (app_user, customer, account). El resto
 * (role, transaction, transfer, loan, payment, audit_log, fraud_alert)
 * no las tiene y por lo tanto extiende directamente Auditable.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class SoftDeletableEntity extends Auditable {

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public void markAsDeleted() {
        this.deleted = true;
        this.deletedAt = Instant.now();
    }
}