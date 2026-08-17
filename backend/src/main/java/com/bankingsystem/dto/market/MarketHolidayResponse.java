package com.bankingsystem.dto.market;

import java.util.List;

public record MarketHolidayResponse(
        String exchange,
        String timezone,
        List<MarketHolidayItemResponse> data) {
}
