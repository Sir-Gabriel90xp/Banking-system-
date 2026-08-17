package com.bankingsystem.events.fraud;

import com.bankingsystem.config.fraud.FraudRabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Publica FraudCheckEvent a RabbitMQ tras una transferencia (CU-07 paso 6).
 * Espejo de AuditEventPublisher del módulo de Auditoría.
 */
@Component
public class FraudCheckPublisher {

    private final RabbitTemplate rabbitTemplate;

    public FraudCheckPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(FraudCheckEvent event) {
        rabbitTemplate.convertAndSend(
                FraudRabbitMQConfig.FRAUD_EXCHANGE,
                FraudRabbitMQConfig.FRAUD_ROUTING_KEY,
                event
        );
    }
}
