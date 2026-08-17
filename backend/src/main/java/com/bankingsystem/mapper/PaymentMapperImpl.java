package com.bankingsystem.mapper;

import org.springframework.stereotype.Component;

import com.bankingsystem.dto.payment.PaymentResponse;
import com.bankingsystem.entities.Payment;

@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toResponse(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentResponse.PaymentResponseBuilder builder = PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus());

        if (payment.getLoan() != null) {
            builder.loanId(payment.getLoan().getId());
        }
        if (payment.getAccount() != null) {
            builder.accountId(payment.getAccount().getId());
            builder.accountNumber(payment.getAccount().getAccountNumber());
        }

        return builder.build();
    }
}
