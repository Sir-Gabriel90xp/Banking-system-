package com.bankingsystem.dto.account.transfer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferResponse(
        UUID id,
        UUID sourceAccount,
        String sourceAccountNumber,
        UUID destinationAccount,
        String destinationAccountNumber,
        BigDecimal amount,
        String status,
        Instant createdAt
) {
}
