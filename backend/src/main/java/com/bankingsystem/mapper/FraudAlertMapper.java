package com.bankingsystem.mapper;

import com.bankingsystem.dto.fraud.FraudAlertResponse;
import com.bankingsystem.entities.FraudAlert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Sin toEntity(): igual que LoanMapper, crear una FraudAlert requiere
 * lógica de negocio (evaluación de reglas) que corresponde al Service,
 * no al Mapper.
 */
@Mapper(componentModel = "spring")
public interface FraudAlertMapper {

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "accountNumber", source = "account.accountNumber")
    FraudAlertResponse toResponse(FraudAlert entity);
}
