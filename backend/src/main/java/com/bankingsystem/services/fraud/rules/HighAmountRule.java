package com.bankingsystem.services.fraud.rules;

import com.bankingsystem.config.fraud.FraudRulesProperties;
import com.bankingsystem.events.fraud.FraudCheckEvent;
import com.bankingsystem.services.fraud.FraudRule;
import com.bankingsystem.services.fraud.FraudSignal;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Regla 1: el monto de la transferencia supera el umbral configurado
 * (fraud.high-amount.threshold).
 */
@Component
public class HighAmountRule implements FraudRule {

    private final FraudRulesProperties properties;

    public HighAmountRule(FraudRulesProperties properties) {
        this.properties = properties;
    }

    @Override
    public Optional<FraudSignal> evaluate(FraudCheckEvent event) {
        var threshold = properties.getHighAmount().getThreshold();

        if (event.getAmount() == null || threshold == null) {
            return Optional.empty();
        }

        if (event.getAmount().compareTo(threshold) > 0) {
            String reason = "Monto de transferencia (%s) supera el umbral configurado (%s)"
                    .formatted(event.getAmount(), threshold);
            return Optional.of(new FraudSignal(reason, properties.getHighAmount().getSeverity()));
        }

        return Optional.empty();
    }
}
