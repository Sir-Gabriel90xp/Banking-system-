package com.bankingsystem.dto.exchange;

public record CurrencyResponse(
        String isoCode,
        String isoNumeric,
        String name,
        String symbol,
        String startDate) {
}
