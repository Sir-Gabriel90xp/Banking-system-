package com.bankingsystem.dto.account.transfer;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Nombres de campo tal como los define 11_endpoints.md (POST /transfers):
 * "sourceAccount" y "destinationAccount" en el JSON, aunque la entidad/
 * columna de base de datos use "origin_account_id" — ver nota en
 * TransferRepository.
 */
public record TransferRequest(
        @NotNull UUID sourceAccount,
        UUID destinationAccount,
        @Size(max = 34, message = "Destination account number cannot exceed 34 characters.")
        String destinationAccountNumber,
        @NotNull @DecimalMin(value = "0.01", message = "Amount must be greater than zero.") BigDecimal amount
) {
}
