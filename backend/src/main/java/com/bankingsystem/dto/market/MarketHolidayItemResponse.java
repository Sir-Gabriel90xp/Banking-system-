package com.bankingsystem.dto.market;

public record MarketHolidayItemResponse(
        String eventName,
        String atDate,
        String tradingHour) {
}
