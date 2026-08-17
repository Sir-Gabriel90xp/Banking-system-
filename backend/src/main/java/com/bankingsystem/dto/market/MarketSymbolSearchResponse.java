package com.bankingsystem.dto.market;

import java.util.List;

public record MarketSymbolSearchResponse(
        int count,
        List<MarketSymbolSearchItemResponse> result) {
}
