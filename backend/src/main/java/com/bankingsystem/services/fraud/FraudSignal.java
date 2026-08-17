package com.bankingsystem.services.fraud;

import com.bankingsystem.entities.enums.FraudSeverity;

/**
 * Resultado de una FraudRule cuando detecta un patrón sospechoso.
 */
public record FraudSignal(String reason, FraudSeverity severity) {
}
