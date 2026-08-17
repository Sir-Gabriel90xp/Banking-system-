package com.bankingsystem.dto.auth.customer;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bankingsystem.entities.enums.AccountType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

/**
 * Request de creación/actualización de un Customer.
 * Corresponde a RF-02.1 (registrar cliente) y RF-02.3 (actualizar cliente).
 *
 * NOTA: los nombres de campos asumen los mismos definidos en 03_database_model.md
 * (firstName, lastName, documentNumber, birthDate, phone, email, address).
 * Si la entidad Customer real (creada en la Sesión 5) usa otros nombres,
 * ajustar este DTO y el mapper en consecuencia.
 */
public record CustomerRequest(

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Document number is required")
        String documentNumber,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        String phone,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        String address,

        Boolean createLoginUser,

        String username,

        String password,

        Boolean createInitialAccount,

        AccountType initialAccountType,

        @DecimalMin(value = "0.00", message = "Initial balance cannot be negative.")
        BigDecimal initialBalance
) {
}
