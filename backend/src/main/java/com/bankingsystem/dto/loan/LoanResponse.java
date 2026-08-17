package com.bankingsystem.dto.loan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.bankingsystem.entities.enums.LoanStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {

    private UUID id;
    private UUID customerId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private Integer termMonths;
    private BigDecimal monthlyPayment;
    private LoanStatus status;
    private LocalDateTime createdAt;
}