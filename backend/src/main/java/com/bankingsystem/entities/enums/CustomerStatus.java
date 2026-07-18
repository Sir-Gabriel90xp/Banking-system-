package com.bankingsystem.entities.enums;

/**
 * Estado de un cliente (columna `status` en la tabla `customer`,
 * V1__init_schema.sql). Se guarda como texto (ACTIVE / INACTIVE)
 * en vez de números, para que sea legible directo en la base de datos.
 */
public enum CustomerStatus {
    ACTIVE,
    INACTIVE
}