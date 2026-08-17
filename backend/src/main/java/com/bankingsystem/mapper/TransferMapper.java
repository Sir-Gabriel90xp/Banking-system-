package com.bankingsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bankingsystem.dto.account.transfer.TransferResponse;
import com.bankingsystem.entities.Transfer;

/**
 * Si el status de Transfer es un enum (y no un String, como se asumió en
 * TransferRepository), MapStruct lo convierte automáticamente a String —
 * no requiere ajuste manual.
 */
@Mapper(componentModel = "spring")
public interface TransferMapper {

    @Mapping(target = "sourceAccount", source = "originAccount.id")
    @Mapping(target = "sourceAccountNumber", source = "originAccount.accountNumber")
    @Mapping(target = "destinationAccount", source = "destinationAccount.id")
    @Mapping(target = "destinationAccountNumber", source = "destinationAccount.accountNumber")
    TransferResponse toResponse(Transfer transfer);
}
