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

import com.bankingsystem.dto.account.AccountRequest;
import com.bankingsystem.dto.account.AccountResponse;
import com.bankingsystem.dto.account.BalanceResponse;
import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.entities.enums.AccountStatus;
import com.bankingsystem.services.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 11_endpoints.md, módulo 4. DELETE /accounts/{id} (soft delete, solo si
 * balance = 0) sigue sin implementar — no estaba en el alcance de esta
 * sesión (Auditoría); queda como pendiente aparte.
 */
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CUSTOMER')")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @Valid @RequestBody AccountRequest request,
            Authentication authentication) {

        AccountResponse created = accountService.createAccount(request, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account created successfully.", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AccountResponse>>> listAccounts(
            @RequestParam(required = false) AccountStatus status,
            @RequestParam(required = false) UUID customerId,
            Authentication authentication,
            Pageable pageable) {

        Page<AccountResponse> accounts = accountService.listAccounts(pageable, status, customerId, authentication);
        return ResponseEntity.ok(ApiResponse.success("Accounts retrieved successfully.", accounts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(
            @PathVariable UUID id,
            Authentication authentication) {

        AccountResponse account = accountService.getAccountById(id, authentication);
        return ResponseEntity.ok(ApiResponse.success("Account retrieved successfully.", account));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(
            @PathVariable UUID id,
            Authentication authentication) {

        BalanceResponse balance = accountService.getBalance(id, authentication);
        return ResponseEntity.ok(ApiResponse.success("Balance retrieved successfully.", balance));
    }

    @PatchMapping("/{id}/block")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<AccountResponse>> block(
            @PathVariable UUID id,
            Authentication authentication) {

        AccountResponse account = accountService.block(id, authentication);
        return ResponseEntity.ok(ApiResponse.success("Account blocked successfully.", account));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<AccountResponse>> activate(
            @PathVariable UUID id,
            Authentication authentication) {

        AccountResponse account = accountService.activate(id, authentication);
        return ResponseEntity.ok(ApiResponse.success("Account activated successfully.", account));
    }
}
