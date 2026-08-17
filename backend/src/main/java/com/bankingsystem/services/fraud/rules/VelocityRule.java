package com.bankingsystem.services.fraud.rules;

import com.bankingsystem.config.fraud.FraudRulesProperties;
import com.bankingsystem.events.fraud.FraudCheckEvent;
import com.bankingsystem.repositories.TransferRepository; // ASUNCIÓN: ya existe (creado junto con TransferService)
import com.bankingsystem.services.fraud.FraudRule;
import com.bankingsystem.services.fraud.FraudSignal;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Regla 2: demasiadas transferencias salientes desde la misma cuenta en
 * una ventana de tiempo corta (fraud.velocity.*).
 *
 * REQUIERE agregar este método a TransferRepository.java (no lo tengo
 * disponible para editarlo directamente):
 *
 *   long countByOriginAccountIdAndCreatedAtAfter(UUID originAccountId, Instant since);
 *
 * Si tu entidad Transfer usa otro nombre de campo para la cuenta origen
 * (p. ej. "sourceAccount" en vez de "originAccount", ver 08_entities.md
 * vs 03_database_model.md que usan nombres distintos), ajusta el nombre
 * derivado del método de Spring Data.
 */
@Component
public class VelocityRule implements FraudRule {

    private final FraudRulesProperties properties;
    private final TransferRepository transferRepository;

    public VelocityRule(FraudRulesProperties properties, TransferRepository transferRepository) {
        this.properties = properties;
        this.transferRepository = transferRepository;
    }

    @Override
    public Optional<FraudSignal> evaluate(FraudCheckEvent event) {
        var velocity = properties.getVelocity();

        Instant since = Instant.now().minus(velocity.getWindowMinutes(), ChronoUnit.MINUTES);
        long recentTransfers = transferRepository.countByOriginAccountIdAndCreatedAtAfter(
                event.getOriginAccountId(), since);

        if (recentTransfers >= velocity.getMaxTransfers()) {
            String reason = "%d transferencias salientes en los últimos %d minutos (máximo permitido: %d)"
                    .formatted(recentTransfers, velocity.getWindowMinutes(), velocity.getMaxTransfers());
            return Optional.of(new FraudSignal(reason, velocity.getSeverity()));
        }

        return Optional.empty();
    }
}
