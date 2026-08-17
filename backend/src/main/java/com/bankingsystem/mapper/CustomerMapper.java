package com.bankingsystem.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.bankingsystem.dto.auth.customer.CustomerRequest;
import com.bankingsystem.dto.auth.customer.CustomerResponse;
import com.bankingsystem.entities.Customer;

/**
 * Toda conversión Entity <-> DTO pasa por acá (02_architecture.md: "Mappers").
 * No debe mezclarse lógica de conversión dentro de CustomerService.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer);

    /**
     * Crea una entidad nueva a partir del request. El status inicial,
     * el id y los campos de auditoría se asignan en el Service, no acá.
     */
    Customer toEntity(CustomerRequest request);

    /**
     * Actualiza una entidad existente en memoria con los datos del request
     * (usado en el update, RF-02.3). Ignora valores nulos del request para
     * permitir actualizaciones parciales si el frontend no envía todos los campos.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(CustomerRequest request, @MappingTarget Customer customer);
}