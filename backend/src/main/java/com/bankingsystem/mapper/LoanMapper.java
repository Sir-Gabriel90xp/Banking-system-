package com.bankingsystem.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.bankingsystem.dto.loan.LoanResponse;
import com.bankingsystem.entities.Loan;

/**
 * No incluye toEntity(LoanRequest): crear un Loan requiere lógica de
 * negocio (calcular monthlyPayment, tomar interestRate de configuración,
 * resolver el Customer autenticado) que no le corresponde a un Mapper.
 * Eso se arma explícitamente en LoanService.requestLoan().
 */
public interface LoanMapper {

    LoanResponse toResponse(Loan loan);

    default LocalDateTime map(Instant value) {
        return value == null ? null : LocalDateTime.ofInstant(value, ZoneOffset.UTC);
    }
}