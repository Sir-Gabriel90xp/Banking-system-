package com.bankingsystem.dto.bpd;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BpdConfirmAccountRequest(
        @NotBlank
        @Pattern(regexp = "CEDULA|RNC|Cedula|Rnc|cedula|rnc", message = "documentType must be CEDULA or RNC.")
        String documentType,

        @NotBlank
        String documentNumber,

        @NotBlank
        String accountNumber) {
}
