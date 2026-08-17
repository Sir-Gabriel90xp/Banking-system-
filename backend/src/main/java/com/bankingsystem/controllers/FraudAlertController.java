package com.bankingsystem.controllers;

import java.util.List; // ASUNCIÓN: mismo envoltorio usado por AuthController (04_api_design.md)
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping; // ASUNCIÓN: la autorización por rol se hace con @PreAuthorize; ajustar si el proyecto usa otro mecanismo en SecurityConfig
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.dto.fraud.FraudAlertResponse;
import com.bankingsystem.services.FraudAlertService;

/**
 * Consulta de alertas de fraude (RF-08.2). Endpoints de solo lectura +
 * resolución manual; la creación es 100% automática (FraudDetectionService).
 *
 * Rutas no confirmadas contra 11_endpoints.md (no lo tengo disponible) —
 * ajustar el path base si no coincide con la convención real del proyecto.
 */
@RestController
@RequestMapping("/api/v1/fraud-alerts")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public class FraudAlertController {

    private final FraudAlertService fraudAlertService;

    public FraudAlertController(FraudAlertService fraudAlertService) {
        this.fraudAlertService = fraudAlertService;
    }

    @GetMapping
    public ApiResponse<List<FraudAlertResponse>> list() {
        return ApiResponse.success(fraudAlertService.list(), "Fraud alerts retrieved successfully.");
    }

    @GetMapping("/{id}")
    public ApiResponse<FraudAlertResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(fraudAlertService.getById(id), "Fraud alert retrieved successfully.");
    }

    @PatchMapping("/{id}/resolve")
    public ApiResponse<FraudAlertResponse> resolve(@PathVariable UUID id) {
        return ApiResponse.success(fraudAlertService.resolve(id), "Fraud alert resolved successfully.");
    }
}
