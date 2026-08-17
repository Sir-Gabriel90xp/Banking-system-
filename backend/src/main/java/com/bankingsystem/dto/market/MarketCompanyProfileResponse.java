package com.bankingsystem.dto.market;

import java.math.BigDecimal;

public record MarketCompanyProfileResponse(
        String country,
        String currency,
        String exchange,
        String ipo,
        BigDecimal marketCapitalization,
        String name,
        String phone,
        BigDecimal shareOutstanding,
        String ticker,
        String weburl,
        String logo,
        String finnhubIndustry) {
}
