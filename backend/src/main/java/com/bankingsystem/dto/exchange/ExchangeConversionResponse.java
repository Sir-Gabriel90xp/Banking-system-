package com.bankingsystem.dto.exchange;

import java.math.BigDecimal;

public record ExchangeConversionResponse(
        String date,
        String base,
        String quote,
        BigDecimal amount,
        BigDecimal rate,
        BigDecimal convertedAmount) {
}
