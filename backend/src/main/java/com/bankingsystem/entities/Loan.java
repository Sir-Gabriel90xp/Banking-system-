package com.bankingsystem.entities;

import com.bankingsystem.entities.base.Auditable;
import com.bankingsystem.entities.enums.LoanStatus;
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

import java.math.BigDecimal;

/**
 * Préstamo bancario (RF-05, RN-04).
 *
 * Mapea la tabla `loan` de V1__init_schema.sql.
 *
 * No tiene soft delete en el esquema, por eso extiende Auditable
 * directamente. `customer` es obligatorio (RN-04: todo préstamo debe
 * pertenecer a un cliente existente).
 */
@Entity
@Table(name = "loan")
@Getter
@Setter
@NoArgsConstructor
public class Loan extends Auditable {

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Column(name = "monthly_payment", nullable = false, precision = 19, scale = 2)
    private BigDecimal monthlyPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private LoanStatus status = LoanStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}