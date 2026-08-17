package com.bankingsystem.dto.trading;

import java.math.BigDecimal;
import java.util.UUID;

public record TradingPositionResponse(
        UUID id,
        String symbol,
        String displaySymbol,
        BigDecimal quantity,
        BigDecimal averagePrice,
        BigDecimal lastPrice,
        BigDecimal marketValue,
        BigDecimal unrealizedPnl) {
}
