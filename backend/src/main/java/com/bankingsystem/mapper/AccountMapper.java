package com.bankingsystem.mapper;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bankingsystem.dto.account.AccountResponse;
import com.bankingsystem.dto.account.BalanceResponse;
import com.bankingsystem.entities.Account;

/**
 * Supuesto a confirmar: que Account tiene getCustomer() (relación @ManyToOne
 * hacia Customer, según 03_database_model.md / 08_entities.md). Si el campo
 * se llama distinto en tu entidad real, ajustar el "source" de @Mapping.
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "customerId", source = "customer.id")
    AccountResponse toResponse(Account account);

    default BalanceResponse toBalanceResponse(Account account) {
        return new BalanceResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getCurrency(),
                Instant.now()
        );
    }
}