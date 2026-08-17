package com.bankingsystem.events.fraud;

import com.bankingsystem.config.fraud.FraudRabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Publica FraudCheckEvent a RabbitMQ tras una transferencia (CU-07 paso 6).
 * Espejo de AuditEventPublisher del módulo de Auditoría.
 */
@Component
public class FraudCheckPublisher {

    private static final Logger log = LoggerFactory.getLogger(FraudCheckPublisher.class);

    private final ObjectProvider<RabbitTemplate> rabbitTemplateProvider;

    @Value("${app.messaging.rabbitmq.enabled:false}")
    private boolean rabbitMqEnabled;

    public FraudCheckPublisher(ObjectProvider<RabbitTemplate> rabbitTemplateProvider) {
        this.rabbitTemplateProvider = rabbitTemplateProvider;
    }

    public void publish(FraudCheckEvent event) {
        if (!rabbitMqEnabled) {
            log.debug("RabbitMQ disabled; skipping fraud check event for transfer {}", event.getTransferId());
            return;
        }

        RabbitTemplate rabbitTemplate = rabbitTemplateProvider.getIfAvailable();
        if (rabbitTemplate == null) {
            log.warn("RabbitMQ enabled but RabbitTemplate is unavailable; skipping fraud check event.");
            return;
        }

        rabbitTemplate.convertAndSend(
                FraudRabbitMQConfig.FRAUD_EXCHANGE,
                FraudRabbitMQConfig.FRAUD_ROUTING_KEY,
                event
        );
    }
}
