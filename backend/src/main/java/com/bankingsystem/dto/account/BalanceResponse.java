package com.bankingsystem.dto.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Respuesta específica para RF-03.2 / GET /api/v1/accounts/{id}/balance.
 * Se separa de AccountResponse porque el consumidor de este endpoint
 * (frontend, apps móviles) normalmente solo necesita el saldo, no el
 * objeto completo de la cuenta.
 */
public record BalanceResponse(
        UUID accountId,
        String accountNumber,
        BigDecimal balance,
        String currency,
        Instant asOf
) {
}