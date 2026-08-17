package com.bankingsystem.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingsystem.entities.Loan;
import com.bankingsystem.entities.enums.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    Page<Loan> findByCustomerId(UUID customerId, Pageable pageable);

    Page<Loan> findByStatus(LoanStatus status, Pageable pageable);

    Page<Loan> findByCustomerIdAndStatus(UUID customerId, LoanStatus status, Pageable pageable);
}