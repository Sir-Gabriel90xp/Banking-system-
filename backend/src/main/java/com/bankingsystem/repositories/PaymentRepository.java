package com.bankingsystem.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingsystem.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Page<Payment> findByLoanId(UUID loanId, Pageable pageable);

    Page<Payment> findByLoan_Customer_Id(UUID customerId, Pageable pageable);

    long countByLoanId(UUID loanId);
}