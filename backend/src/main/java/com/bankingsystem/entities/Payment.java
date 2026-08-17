package com.bankingsystem.entities;

import java.math.BigDecimal;
import java.time.Instant;

import com.bankingsystem.entities.base.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pago registrado (RF-06).
 *
 * Mapea la tabla `payment` de V1__init_schema.sql + V3__add_payment_account.sql.
 * `loan` y `account` son opcionales a nivel de base de datos para preservar
 * compatibilidad con registros existentes y permitir pagos de servicios en una
 * fase futura. El endpoint actual de pago de prestamo exige ambos campos.
 */
@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends Auditable {

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private Instant paymentDate = Instant.now();

    @Column(name = "payment_method", nullable = false, length = 30)
    private String paymentMethod;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "COMPLETED";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
