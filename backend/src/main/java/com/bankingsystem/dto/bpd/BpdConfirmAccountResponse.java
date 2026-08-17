package com.bankingsystem.dto.bpd;

public record BpdConfirmAccountResponse(
        boolean status,
        String message) {
}
