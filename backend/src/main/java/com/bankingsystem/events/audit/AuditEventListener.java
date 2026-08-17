package com.bankingsystem.events.audit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.bankingsystem.config.RabbitMQConfig;
import com.bankingsystem.entities.AuditLog;
import com.bankingsystem.repositories.AppUserRepository;
import com.bankingsystem.repositories.AuditLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RF-07.3: consumidor asíncrono que persiste el AuditEvent como AuditLog.
 * Vive fuera del flujo HTTP principal: si esto falla, no afecta ninguna
 * respuesta ya enviada al cliente.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditEventListener {

    private final AuditLogRepository auditLogRepository;
    private final AppUserRepository appUserRepository;

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    public void onAuditEvent(AuditEvent event) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(event.action());
        auditLog.setEntity(event.entity());
        auditLog.setEntityId(event.entityId());
        auditLog.setIpAddress(event.ipAddress());
        auditLog.setTimestamp(event.timestamp());

        if (event.userId() != null) {
            appUserRepository.findById(event.userId()).ifPresentOrElse(
                    auditLog::setUser,
                    () -> log.warn("Audit event references unknown userId={}", event.userId()));
        }

        auditLogRepository.save(auditLog);
    }
}