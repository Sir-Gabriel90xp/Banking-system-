package com.bankingsystem.mapper;

import org.springframework.stereotype.Component;

import com.bankingsystem.dto.loan.LoanResponse;
import com.bankingsystem.entities.Customer;
import com.bankingsystem.entities.Loan;

@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public LoanResponse toResponse(Loan loan) {
        if (loan == null) {
            return null;
        }

        LoanResponse.LoanResponseBuilder builder = LoanResponse.builder();
        builder.id(loan.getId());
        builder.amount(loan.getAmount());
        builder.interestRate(loan.getInterestRate());
        builder.termMonths(loan.getTermMonths());
        builder.monthlyPayment(loan.getMonthlyPayment());
        builder.status(loan.getStatus());
        builder.createdAt(map(loan.getCreatedAt()));

        Customer customer = loan.getCustomer();
        if (customer != null && customer.getId() != null) {
            builder.customerId(customer.getId());
        }

        return builder.build();
    }
}
