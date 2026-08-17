package com.bankingsystem.dto.payment;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Registro de pago de cuota de préstamo (RF-06.1, CU-10).
 * Alcance actual: solo pagos asociados a un Loan; pago de servicios
 * (RF-06.2) queda pendiente para una fase futura (loan quedaría null).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @NotNull(message = "Loan id is required.")
    private UUID loanId;

    @NotNull(message = "Account id is required.")
    private UUID accountId;

    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
    private BigDecimal amount;

    @NotBlank(message = "Payment method is required.")
    private String paymentMethod;
}
