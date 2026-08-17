package com.bankingsystem.dto.trading;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.bankingsystem.entities.enums.TradingOrderSide;
import com.bankingsystem.entities.enums.TradingOrderStatus;

public record TradingOrderResponse(
        UUID id,
        String symbol,
        String displaySymbol,
        TradingOrderSide side,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal grossAmount,
        TradingOrderStatus status,
        Instant createdAt) {
}
