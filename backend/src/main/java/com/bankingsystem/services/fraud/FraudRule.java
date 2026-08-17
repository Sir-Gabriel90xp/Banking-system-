package com.bankingsystem.services.fraud;

import com.bankingsystem.events.fraud.FraudCheckEvent;

import java.util.Optional;

/**
 * Contrato de una regla de detección de fraude (RF-08.1).
 *
 * Cada regla se registra como @Component y Spring inyecta automáticamente
 * la lista completa en FraudDetectionService — agregar una regla nueva no
 * requiere modificar el Service (Open/Closed Principle, PROJECT_CONTEXT.md).
 */
public interface FraudRule {

    /**
     * Evalúa el evento. Devuelve un FraudSignal si la regla detecta un
     * patrón sospechoso, o Optional.empty() si no.
     */
    Optional<FraudSignal> evaluate(FraudCheckEvent event);
}
