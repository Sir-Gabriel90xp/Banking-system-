package com.bankingsystem.dto.market;

import java.util.Map;

public record MarketBasicFinancialsResponse(
        String symbol,
        Map<String, Object> metric) {
}
