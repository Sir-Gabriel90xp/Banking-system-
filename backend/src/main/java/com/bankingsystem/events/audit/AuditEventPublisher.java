package com.bankingsystem.events.audit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bankingsystem.config.RabbitMQConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Punto único de publicación de eventos de auditoría. Los demás Services
 * (Auth, Transfer, Loan, Account) lo inyectan y llaman a publish() tras
 * completar su operación de negocio — nunca antes, para no auditar
 * acciones que terminaron fallando.
 *
 * Si RabbitMQ no está disponible, el error se loggea pero NO se relanza:
 * una falla de auditoría no debe tumbar la operación de negocio principal
 * (ej. una transferencia exitosa no debe fallar solo porque no se pudo
 * publicar el evento). Esto es una decisión de diseño, no un descuido —
 * si se requiere garantía estricta de auditoría, debe registrarse como
 * ADR aparte (ej. outbox pattern).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditEventPublisher {

    private final ObjectProvider<RabbitTemplate> rabbitTemplateProvider;

    @Value("${app.messaging.rabbitmq.enabled:false}")
    private boolean rabbitMqEnabled;

    public void publish(AuditEvent event) {
        if (!rabbitMqEnabled) {
            log.debug("RabbitMQ disabled; skipping audit event: action={}, entity={}, entityId={}",
                    event.action(), event.entity(), event.entityId());
            return;
        }

        RabbitTemplate rabbitTemplate = rabbitTemplateProvider.getIfAvailable();
        if (rabbitTemplate == null) {
            log.warn("RabbitMQ enabled but RabbitTemplate is unavailable; skipping audit event.");
            return;
        }

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.AUDIT_EXCHANGE,
                    RabbitMQConfig.AUDIT_ROUTING_KEY,
                    event);
        } catch (Exception ex) {
            log.error("Failed to publish audit event: action={}, entity={}, entityId={}",
                    event.action(), event.entity(), event.entityId(), ex);
        }
    }
}
