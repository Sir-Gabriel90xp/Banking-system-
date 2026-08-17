package com.bankingsystem.dto.exchange;

import java.math.BigDecimal;
import java.util.List;

public record ExchangeRateResponse(
        String date,
        String base,
        String quote,
        BigDecimal rate,
        List<ExchangeRateProviderContributionResponse> providers) {
}
