package com.bankingsystem.dto.auth.customer;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.bankingsystem.entities.enums.CustomerStatus;

/**
 * Response expuesto por la API para un Customer.
 * No incluye campos de auditoría sensibles (createdBy/updatedBy) por decisión
 * de diseño simple; agregar si el frontend los llega a necesitar.
 */
public record CustomerResponse(

        UUID id,
        String firstName,
        String lastName,
        String documentNumber,
        LocalDate birthDate,
        String phone,
        String email,
        String address,
        CustomerStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}