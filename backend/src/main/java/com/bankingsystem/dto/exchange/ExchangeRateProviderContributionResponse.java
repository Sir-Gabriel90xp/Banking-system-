package com.bankingsystem.dto.exchange;

import java.math.BigDecimal;

public record ExchangeRateProviderContributionResponse(
        String key,
        String date,
        BigDecimal rate,
        Boolean excluded) {
}
