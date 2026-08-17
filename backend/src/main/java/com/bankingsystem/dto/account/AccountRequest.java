package com.bankingsystem.dto.account;

import java.math.BigDecimal;
import java.util.UUID;

import com.bankingsystem.entities.enums.AccountType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** CU-04: creación de cuenta. accountNumber, balance, status y currency los asigna el sistema. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    private UUID customerId;

    @NotNull(message = "Account type is required.")
    private AccountType accountType;

    @DecimalMin(value = "0.00", message = "Initial balance cannot be negative.")
    private BigDecimal initialBalance;
}
