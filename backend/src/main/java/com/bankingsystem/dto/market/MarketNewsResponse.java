package com.bankingsystem.dto.market;

public record MarketNewsResponse(
        String category,
        Long datetime,
        String headline,
        Long id,
        String image,
        String related,
        String source,
        String summary,
        String url) {
}
