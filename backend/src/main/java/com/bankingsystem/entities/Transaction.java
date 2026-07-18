package com.bankingsystem.entities;

import java.math.BigDecimal;
import java.time.Instant;

import com.bankingsystem.entities.base.Auditable;
import com.bankingsystem.entities.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Movimiento financiero general (RF-04, RF-06).
 *
 * Mapea la tabla `transaction` de V1__init_schema.sql.
 *
 * No tiene soft delete en el esquema (una transacción no se "borra"
 * lógicamente), por eso extiende Auditable directamente.
 *
 * Nota: `status` se deja como texto simple (no enum) porque el SQL no
 * define una lista cerrada de valores para esta columna, solo un
 * default ('COMPLETED'). Si más adelante se define el dominio completo
 * de estados, se puede convertir a enum igual que TransactionType.
 */
@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends Auditable {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TransactionType type;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "date", nullable = false)
    private Instant date = Instant.now();

    @Column(name = "status", nullable = false, length = 20)
    private String status = "COMPLETED";

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}