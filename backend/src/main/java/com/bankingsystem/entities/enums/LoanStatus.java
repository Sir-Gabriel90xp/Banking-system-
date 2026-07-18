package com.bankingsystem.entities.enums;

/**
 * Estado de un préstamo (columna `status` en la tabla `loan`,
 * V1__init_schema.sql). Coincide con RF-05.5.
 */
public enum LoanStatus {
    PENDING,
    APPROVED,
    REJECTED,
    PAID
}