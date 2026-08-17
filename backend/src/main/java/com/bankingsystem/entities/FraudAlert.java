package com.bankingsystem.entities;

import java.time.Instant; // ASUNCIÓN: misma clase base usada por las otras 10 entidades (createdAt/updatedAt/createdBy/updatedBy, RNF-07)

import com.bankingsystem.entities.base.Auditable;
import com.bankingsystem.entities.enums.FraudAlertStatus;
import com.bankingsystem.entities.enums.FraudSeverity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Alerta de fraude generada por FraudDetectionService (RF-08.2, CU-11).
 */
@Entity
@Table(name = "fraud_alert")
public class FraudAlert extends Auditable {

    // ASUNCIÓN: la entidad Account existe en com.bankingsystem.entities y su PK es UUID.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, length = 255)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FraudSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FraudAlertStatus status = FraudAlertStatus.OPEN;

    @Column(name = "detected_at", nullable = false)
    private Instant detectedAt;

    protected FraudAlert() {
        // JPA
    }

    public FraudAlert(Account account, String reason, FraudSeverity severity, Instant detectedAt) {
        this.account = account;
        this.reason = reason;
        this.severity = severity;
        this.detectedAt = detectedAt;
        this.status = FraudAlertStatus.OPEN;
    }

    public void resolve() {
        this.status = FraudAlertStatus.RESOLVED;
    }

    public Account getAccount() {
        return account;
    }

    public String getReason() {
        return reason;
    }

    public FraudSeverity getSeverity() {
        return severity;
    }

    public FraudAlertStatus getStatus() {
        return status;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }
}
