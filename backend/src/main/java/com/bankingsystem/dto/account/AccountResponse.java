package com.bankingsystem.dto.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.bankingsystem.entities.enums.AccountStatus;
import com.bankingsystem.entities.enums.AccountType;

/**
 * Nunca se expone la entidad Account directamente (RNF-03 / ADR-004).
 *
 * Supuesto a confirmar contra la entidad real: nombre del campo de tipo de
 * cuenta. 03_database_model.md usa "accountType"; 08_entities.md usa "type".
 * Ajustar el mapper si el nombre real difiere.
 */
public record AccountResponse(
        UUID id,
        String accountNumber,
        AccountType accountType,
        BigDecimal balance,
        String currency,
        AccountStatus status,
        UUID customerId,
        Instant createdAt
) {
}