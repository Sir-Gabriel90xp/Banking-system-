package com.bankingsystem.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.dto.loan.LoanRequest;
import com.bankingsystem.dto.loan.LoanResponse;
import com.bankingsystem.entities.enums.LoanStatus;
import com.bankingsystem.services.LoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Endpoints de Loans (11_endpoints.md, módulo 6). RF-05, CU-08, CU-09.
 *
 * NOTA sobre roles: CU-08 dice que el actor de la solicitud es Customer;
 * CU-09 dice que aprobar/rechazar es responsabilidad de Employee. Se
 * restringe approve/reject a ADMIN+EMPLOYEE por consistencia con el
 * mismo criterio ya usado en CustomerController (create/update/delete
 * de Customer también son ADMIN+EMPLOYEE). Ajustar si se decide que
 * ADMIN no debería poder aprobar préstamos directamente.
 */
@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<LoanResponse>> requestLoan(
            @Valid @RequestBody LoanRequest request,
            Authentication authentication) {

        LoanResponse created = loanService.requestLoan(request, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Loan request submitted successfully."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<LoanResponse>>> list(
            @RequestParam(required = false) UUID customerId,
            @RequestParam(required = false) LoanStatus status,
            Pageable pageable,
            Authentication authentication) {

        Page<LoanResponse> loans = loanService.list(customerId, status, pageable, authentication);
        return ResponseEntity.ok(ApiResponse.success(loans, "Loans retrieved successfully."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanResponse>> getById(
            @PathVariable UUID id,
            Authentication authentication) {

        LoanResponse loan = loanService.getById(id, authentication);
        return ResponseEntity.ok(ApiResponse.success(loan, "Loan retrieved successfully."));
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<LoanResponse>> approve(@PathVariable UUID id) {
        LoanResponse loan = loanService.approve(id);
        return ResponseEntity.ok(ApiResponse.success(loan, "Loan approved successfully."));
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<LoanResponse>> reject(@PathVariable UUID id) {
        LoanResponse loan = loanService.reject(id);
        return ResponseEntity.ok(ApiResponse.success(loan, "Loan rejected successfully."));
    }
}