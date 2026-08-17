package com.bankingsystem.entities.enums;

/**
 * Nivel de severidad de una alerta de fraude (columna `severity` en la
 * tabla `fraud_alert`, V1__init_schema.sql). Coincide con RF-08.2.
 */
public enum FraudSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}