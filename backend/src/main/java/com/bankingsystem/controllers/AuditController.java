package com.bankingsystem.controllers;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.audit.AuditLogResponse;
import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.services.AuditService;

import lombok.RequiredArgsConstructor;

/** Endpoints de Audit (11_endpoints.md, módulo 9). Solo lectura, ROLE_ADMIN. */
@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AuditLogResponse>>> list(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String entity,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            Pageable pageable) {

        Page<AuditLogResponse> logs = auditService.search(userId, entity, from, to, pageable);
        return ResponseEntity.ok(ApiResponse.success(logs, "Audit logs retrieved successfully."));
    }
}