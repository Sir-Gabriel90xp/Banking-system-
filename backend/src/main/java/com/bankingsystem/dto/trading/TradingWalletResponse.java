package com.bankingsystem.dto.trading;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record TradingWalletResponse(
        UUID id,
        UUID customerId,
        BigDecimal cashBalance,
        String currency,
        BigDecimal portfolioValue,
        BigDecimal totalEquity,
        List<TradingPositionResponse> positions) {
}
