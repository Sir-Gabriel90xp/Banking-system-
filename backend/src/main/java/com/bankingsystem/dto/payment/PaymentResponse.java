package com.bankingsystem.dto.payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private UUID id;
    private UUID loanId;
    private UUID accountId;
    private String accountNumber;
    private BigDecimal amount;
    private Instant paymentDate;
    private String paymentMethod;
    private String status;
}
