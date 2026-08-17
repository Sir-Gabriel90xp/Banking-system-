package com.bankingsystem.dto.market;

public record MarketStatusResponse(
        String exchange,
        String holiday,
        Boolean isOpen,
        String session,
        String timezone,
        Long timestamp) {
}
