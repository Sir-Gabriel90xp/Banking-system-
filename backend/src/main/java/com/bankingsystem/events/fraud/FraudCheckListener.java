package com.bankingsystem.events.fraud;

import com.bankingsystem.config.fraud.FraudRabbitMQConfig;
import com.bankingsystem.services.fraud.FraudDetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Consumidor asíncrono del flujo de detección de fraude (RF-08.4).
 * Espejo de AuditEventListener del módulo de Auditoría.
 *
 * Nota sobre fallos: por ahora no hay dead-letter queue configurada
 * (mismo estado que AuditEventListener, ver punto pendiente en
 * bitacora.md Sesión 8, Ronda 2). Si evaluate() lanza una excepción,
 * el comportamiento por defecto de Spring AMQP es reintentar/rechazar
 * según la configuración global de listener — revisar antes de producción.
 */
@Component
@ConditionalOnProperty(name = "app.messaging.rabbitmq.enabled", havingValue = "true")
public class FraudCheckListener {

    private static final Logger log = LoggerFactory.getLogger(FraudCheckListener.class);

    private final FraudDetectionService fraudDetectionService;

    public FraudCheckListener(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }

    @RabbitListener(queues = FraudRabbitMQConfig.FRAUD_QUEUE)
    public void onFraudCheckEvent(FraudCheckEvent event) {
        log.debug("Evaluando fraude para transferencia {}", event.getTransferId());
        fraudDetectionService.evaluate(event);
    }
}
