package com.bankingsystem.dto.fraud;

import com.bankingsystem.entities.enums.FraudAlertStatus;
import com.bankingsystem.entities.enums.FraudSeverity;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO de salida de FraudAlert. Nunca se expone la entidad directamente
 * (RNF-03, 02_architecture.md).
 */
public record FraudAlertResponse(
        UUID id,
        UUID accountId,
        String accountNumber, // ASUNCIÓN: Account expone getAccountNumber()
        String reason,
        FraudSeverity severity,
        FraudAlertStatus status,
        Instant detectedAt
) {
}
