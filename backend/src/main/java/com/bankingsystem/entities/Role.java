package com.bankingsystem.entities;

import com.bankingsystem.entities.base.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rol del sistema (RF-01.6): ADMIN, EMPLOYEE, CUSTOMER.
 *
 * Mapea la tabla `role` creada en V1__init_schema.sql.
 * No tiene soft delete en el esquema, por eso extiende Auditable
 * directamente (no SoftDeletableEntity).
 */
@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class Role extends Auditable {

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}