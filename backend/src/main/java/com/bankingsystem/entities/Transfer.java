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
 * Transferencia entre cuentas (RF-04, RN-01, RN-02).
 *
 * Mapea la tabla `transfer` de V1__init_schema.sql.
 *
 * La regla "origen y destino deben ser distintos" (RN-02) ya está
 * garantizada a nivel de base de datos con el constraint
 * chk_transfer_different_accounts. Esta entidad no la repite; la
 * capa de Service la validará también, para poder devolver un mensaje
 * de error claro (422) antes de llegar a la base de datos.
 *
 * No tiene soft delete en el esquema, por eso extiende Auditable
 * directamente. `status` se deja como texto simple por la misma razón
 * que en Transaction (el SQL no define una lista cerrada de valores).
 */
@Entity
@Table(name = "transfer")
@Getter
@Setter
@NoArgsConstructor
public class Transfer extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "origin_account_id", nullable = false)
    private Account originAccount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destination_account_id", nullable = false)
    private Account destinationAccount;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "COMPLETED";

    @Column(name = "transfer_date", nullable = false)
    private Instant transferDate = Instant.now();
}