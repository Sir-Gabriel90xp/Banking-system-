package com.bankingsystem.entities.enums;

/**
 * Estado de una cuenta bancaria (columna `status` en la tabla `account`,
 * V1__init_schema.sql).
 *
 * BLOCKED se usa tanto para bloqueos manuales (RF-03.3) como automáticos
 * por fraude (RF-08.3, CU-06).
 */
public enum AccountStatus {
    ACTIVE,
    BLOCKED,
    CLOSED
}

