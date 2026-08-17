package com.bankingsystem.dto.market;

import java.math.BigDecimal;

public record MarketQuoteResponse(
        String symbol,
        BigDecimal currentPrice,
        BigDecimal change,
        BigDecimal percentChange,
        BigDecimal highPriceOfDay,
        BigDecimal lowPriceOfDay,
        BigDecimal openPriceOfDay,
        BigDecimal previousClosePrice,
        Long timestamp) {
}
