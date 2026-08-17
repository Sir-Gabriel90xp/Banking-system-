package com.bankingsystem.mapper;

import com.bankingsystem.dto.payment.PaymentResponse;
import com.bankingsystem.entities.Payment;

public interface PaymentMapper {

    PaymentResponse toResponse(Payment payment);
}