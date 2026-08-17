package com.bankingsystem.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.dto.payment.PaymentRequest;
import com.bankingsystem.dto.payment.PaymentResponse;
import com.bankingsystem.services.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Endpoints de Payments (11_endpoints.md, módulo 7). RF-06, CU-10.
 *
 * NOTA sobre roles: RF-06.1 no especifica quién registra el pago; se
 * asume CUSTOMER (paga su propia cuota), consistente con CU-10 donde
 * el actor es Customer. ADMIN/EMPLOYEE no están restringidos aquí para
 * permitir registrar pagos en nombre de un cliente si hace falta
 * (ej. pago en ventanilla) — ajustar si se decide lo contrario.
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> registerPayment(
            @Valid @RequestBody PaymentRequest request,
            Authentication authentication) {

        PaymentResponse created = paymentService.registerPayment(request, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Payment registered successfully."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> list(
            @RequestParam(required = false) UUID loanId,
            Pageable pageable,
            Authentication authentication) {

        Page<PaymentResponse> payments = paymentService.list(loanId, pageable, authentication);
        return ResponseEntity.ok(ApiResponse.success(payments, "Payments retrieved successfully."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getById(
            @PathVariable UUID id,
            Authentication authentication) {

        PaymentResponse payment = paymentService.getById(id, authentication);
        return ResponseEntity.ok(ApiResponse.success(payment, "Payment retrieved successfully."));
    }
}