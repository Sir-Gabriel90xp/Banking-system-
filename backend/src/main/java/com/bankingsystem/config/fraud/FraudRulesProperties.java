package com.bankingsystem.config.fraud;

import com.bankingsystem.entities.enums.FraudSeverity;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

/**
 * Vincula las reglas de detección de fraude configuradas en application.yml.
 * Mismo criterio que ADR-009 (tasa de interés del préstamo): valores de
 * negocio ajustables sin recompilar, no hardcodeados en el Service.
 *
 * Agregar en application.yml (y en application-dev.yml/application-docker.yml
 * si sobreescriben valores):
 *
 * fraud:
 *   auto-block-severity: HIGH
 *   high-amount:
 *     threshold: 10000.00
 *     severity: HIGH
 *   velocity:
 *     max-transfers: 5
 *     window-minutes: 10
 *     severity: MEDIUM
 */
@ConfigurationProperties(prefix = "fraud")
public class FraudRulesProperties {

    /**
     * Severidad mínima que dispara el bloqueo automático de la cuenta (RF-08.3).
     * Se compara vía FraudSeverity#compareTo.
     */
    private FraudSeverity autoBlockSeverity = FraudSeverity.HIGH;

    private HighAmount highAmount = new HighAmount();
    private Velocity velocity = new Velocity();

    public FraudSeverity getAutoBlockSeverity() {
        return autoBlockSeverity;
    }

    public void setAutoBlockSeverity(FraudSeverity autoBlockSeverity) {
        this.autoBlockSeverity = autoBlockSeverity;
    }

    public HighAmount getHighAmount() {
        return highAmount;
    }

    public void setHighAmount(HighAmount highAmount) {
        this.highAmount = highAmount;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    /** Regla 1: monto de la transferencia por encima de un umbral fijo. */
    public static class HighAmount {
        private BigDecimal threshold = new BigDecimal("10000.00");
        private FraudSeverity severity = FraudSeverity.HIGH;

        public BigDecimal getThreshold() {
            return threshold;
        }

        public void setThreshold(BigDecimal threshold) {
            this.threshold = threshold;
        }

        public FraudSeverity getSeverity() {
            return severity;
        }

        public void setSeverity(FraudSeverity severity) {
            this.severity = severity;
        }
    }

    /** Regla 2: demasiadas transferencias salientes en una ventana de tiempo corta. */
    public static class Velocity {
        private int maxTransfers = 5;
        private int windowMinutes = 10;
        private FraudSeverity severity = FraudSeverity.MEDIUM;

        public int getMaxTransfers() {
            return maxTransfers;
        }

        public void setMaxTransfers(int maxTransfers) {
            this.maxTransfers = maxTransfers;
        }

        public int getWindowMinutes() {
            return windowMinutes;
        }

        public void setWindowMinutes(int windowMinutes) {
            this.windowMinutes = windowMinutes;
        }

        public FraudSeverity getSeverity() {
            return severity;
        }

        public void setSeverity(FraudSeverity severity) {
            this.severity = severity;
        }
    }
}
