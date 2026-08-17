package com.bankingsystem.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingsystem.entities.Account;
import com.bankingsystem.entities.enums.AccountStatus;
import com.bankingsystem.entities.enums.AccountType;

/**
 * Repositorio de Account.
 *
 * Soporta las consultas paginadas requeridas por GET /api/v1/accounts
 * (filtros opcionales ?status= y ?customerId=, ver 11_endpoints.md).
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByCustomerIdAndAccountTypeAndDeletedFalse(UUID customerId, AccountType accountType);

    Page<Account> findByCustomerId(UUID customerId, Pageable pageable);

    Page<Account> findByStatus(AccountStatus status, Pageable pageable);

    Page<Account> findByCustomerIdAndStatus(UUID customerId, AccountStatus status, Pageable pageable);
}
