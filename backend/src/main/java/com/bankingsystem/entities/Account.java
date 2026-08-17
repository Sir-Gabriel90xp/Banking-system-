package com.bankingsystem.entities;

import com.bankingsystem.entities.base.SoftDeletableEntity;
import com.bankingsystem.entities.enums.AccountStatus;
import com.bankingsystem.entities.enums.AccountType;
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
 * Cuenta bancaria (RF-03, RN-01).
 *
 * Mapea la tabla `account` de V1__init_schema.sql.
 *
 * Importante: la regla "no se permiten balances negativos" (RN-01) ya
 * está garantizada a nivel de base de datos con un CHECK (balance >= 0).
 * Esta entidad no la vuelve a validar; eso es responsabilidad de la
 * capa de Service más adelante (02_architecture.md: la Entity no
 * contiene reglas de negocio).
 */
@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account extends SoftDeletableEntity {

    @Column(name = "account_number", nullable = false, unique = true, length = 34)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private AccountType accountType;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "DOP";

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountStatus status = AccountStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}