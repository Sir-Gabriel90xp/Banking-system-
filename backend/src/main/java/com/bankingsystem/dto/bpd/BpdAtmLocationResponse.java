package com.bankingsystem.dto.bpd;

import java.math.BigDecimal;

public record BpdAtmLocationResponse(
        String id,
        String name,
        String category,
        String address,
        String schedule,
        BigDecimal latitude,
        BigDecimal longitude) {
}
