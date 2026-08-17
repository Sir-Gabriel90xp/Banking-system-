package com.bankingsystem.dto.trading;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TradingOrderRequest(
        @NotBlank(message = "Symbol is required.")
        @Size(max = 40, message = "Symbol cannot exceed 40 characters.")
        String symbol,

        @NotNull(message = "Quantity is required.")
        @DecimalMin(value = "0.000001", message = "Quantity must be greater than zero.")
        BigDecimal quantity) {
}
