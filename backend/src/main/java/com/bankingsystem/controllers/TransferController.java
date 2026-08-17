package com.bankingsystem.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.account.transfer.TransferRequest;
import com.bankingsystem.dto.account.transfer.TransferResponse;
import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.services.TransferService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    // POST /api/v1/transfers
    @PostMapping
    public ResponseEntity<ApiResponse<TransferResponse>> createTransfer(
            @Valid @RequestBody TransferRequest request,
            Authentication authentication) {

        TransferResponse transfer = transferService.createTransfer(request, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Transfer completed successfully.", transfer));
    }

    // GET /api/v1/transfers/history?accountId=&status=&page=&size=&sort=
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<Page<TransferResponse>>> getHistory(
            @RequestParam(required = false) UUID accountId,
            @RequestParam(required = false) String status,
            Authentication authentication,
            Pageable pageable) {

        Page<TransferResponse> history = transferService.getHistory(pageable, accountId, status, authentication);
        return ResponseEntity.ok(ApiResponse.success("Transfer history retrieved successfully.", history));
    }
}
