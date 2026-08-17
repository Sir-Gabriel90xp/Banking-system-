package com.bankingsystem.dto.market;

public record MarketSymbolSearchItemResponse(
        String description,
        String displaySymbol,
        String symbol,
        String type) {
}
