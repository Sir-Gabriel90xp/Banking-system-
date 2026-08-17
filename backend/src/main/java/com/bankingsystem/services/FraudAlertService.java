package com.bankingsystem.services;

import com.bankingsystem.dto.fraud.FraudAlertResponse;
import com.bankingsystem.entities.FraudAlert;
import com.bankingsystem.mapper.FraudAlertMapper;
import com.bankingsystem.repositories.FraudAlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException; // ADR-008: mismo criterio provisional que AuthService/GlobalExceptionHandler
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

/**
 * Consulta de alertas de fraude. Solo lectura + resolución manual.
 * La creación de alertas es responsabilidad exclusiva de
 * FraudDetectionService (proceso automático, RF-08.1/RF-08.2).
 *
 * Acceso: ADMIN/EMPLOYEE únicamente (las alertas de fraude son un dato
 * interno del banco, no algo que un CUSTOMER deba consultar). La
 * restricción de rol se aplica en el Controller vía Spring Security,
 * igual que el resto de módulos (RNF-01).
 */
@Service
public class FraudAlertService {

    private final FraudAlertRepository fraudAlertRepository;
    private final FraudAlertMapper fraudAlertMapper;

    public FraudAlertService(FraudAlertRepository fraudAlertRepository, FraudAlertMapper fraudAlertMapper) {
        this.fraudAlertRepository = fraudAlertRepository;
        this.fraudAlertMapper = fraudAlertMapper;
    }

    public List<FraudAlertResponse> list() {
        return fraudAlertRepository.findAll().stream()
                .map(fraudAlertMapper::toResponse)
                .toList();
    }

    public FraudAlertResponse getById(UUID id) {
        FraudAlert alert = findOrThrow(id);
        return fraudAlertMapper.toResponse(alert);
    }

    public FraudAlertResponse resolve(UUID id) {
        FraudAlert alert = findOrThrow(id);
        alert.resolve();
        fraudAlertRepository.save(alert);
        return fraudAlertMapper.toResponse(alert);
    }

    private FraudAlert findOrThrow(UUID id) {
        return fraudAlertRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FraudAlert no encontrada: " + id));
    }
}
