package com.bankingsystem.dto.loan;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Solicitud de préstamo (CU-08, RF-05.1).
 * El customer solicitante se resuelve del usuario autenticado; NO viaja
 * en el body, para que un CUSTOMER no pueda solicitar a nombre de otro.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {

    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
    private BigDecimal amount;

    @NotNull(message = "Term in months is required.")
    @Min(value = 1, message = "Term must be at least 1 month.")
    @Max(value = 360, message = "Term must not exceed 360 months.")
    private Integer termMonths;
}