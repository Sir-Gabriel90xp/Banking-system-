package com.bankingsystem.dto.market;

public record MarketRecommendationTrendResponse(
        Integer buy,
        Integer hold,
        String period,
        Integer sell,
        Integer strongBuy,
        Integer strongSell,
        String symbol) {
}
