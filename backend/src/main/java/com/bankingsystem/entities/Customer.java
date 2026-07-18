package com.bankingsystem.entities;

import java.time.LocalDate;

import com.bankingsystem.entities.base.SoftDeletableEntity;
import com.bankingsystem.entities.enums.CustomerStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Cliente bancario (RF-02, RN-03).
 *
 * Mapea la tabla `customer` de V1__init_schema.sql.
 *
 * Nota: user_id es opcional y no tiene restricción UNIQUE en la base de
 * datos (aunque el modelo conceptual habla de 1 a 1). Se mapea como
 * ManyToOne para reflejar exactamente lo que la base de datos permite hoy.
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends SoftDeletableEntity {

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "document_number", nullable = false, unique = true, length = 50)
    private String documentNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "address", length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CustomerStatus status = CustomerStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;
}