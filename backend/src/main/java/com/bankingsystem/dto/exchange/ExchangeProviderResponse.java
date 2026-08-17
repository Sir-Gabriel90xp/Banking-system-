package com.bankingsystem.dto.exchange;

public record ExchangeProviderResponse(
        String key,
        String name,
        String countryCode,
        String rateType,
        String pivotCurrency) {
}
