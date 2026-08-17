package com.bankingsystem.events.fraud;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Evento publicado tras una operación sensible (por ahora: una transferencia
 * completada) para que FraudCheckListener la evalúe de forma asíncrona
 * (RF-08.1, RF-08.4, CU-07 paso 6, CU-11).
 *
 * Debe ser serializable a JSON por el MessageConverter configurado para
 * RabbitMQ (mismo mecanismo que ya usa AuditEvent).
 */
public class FraudCheckEvent implements Serializable {

    private UUID transferId;
    private UUID originAccountId;
    private UUID destinationAccountId;
    private BigDecimal amount;
    private Instant occurredAt;

    public FraudCheckEvent() {
        // requerido por el deserializador JSON
    }

    public FraudCheckEvent(UUID transferId, UUID originAccountId, UUID destinationAccountId,
                            BigDecimal amount, Instant occurredAt) {
        this.transferId = transferId;
        this.originAccountId = originAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.occurredAt = occurredAt;
    }

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public UUID getOriginAccountId() {
        return originAccountId;
    }

    public void setOriginAccountId(UUID originAccountId) {
        this.originAccountId = originAccountId;
    }

    public UUID getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(UUID destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }
}
