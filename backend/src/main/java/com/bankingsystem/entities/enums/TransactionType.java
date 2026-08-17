package com.bankingsystem.entities.enums;

/**
 * Tipo de movimiento financiero (columna `type` en la tabla
 * `transaction`, V1__init_schema.sql). Valores tomados del comentario
 * original de la migración.
 */
public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER,
    LOAN_PAYMENT
}