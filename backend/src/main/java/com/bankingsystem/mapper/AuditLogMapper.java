package com.bankingsystem.mapper;

import org.springframework.stereotype.Component;

import com.bankingsystem.dto.audit.AuditLogResponse;
import com.bankingsystem.entities.AuditLog;

@Component
public class AuditLogMapper {

    public AuditLogResponse toResponse(AuditLog auditLog) {
        if (auditLog == null) {
            return null;
        }

        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .userId(auditLog.getUser() == null ? null : auditLog.getUser().getId())
                .username(auditLog.getUser() == null ? null : auditLog.getUser().getUsername())
                .action(auditLog.getAction())
                .entity(auditLog.getEntity())
                .entityId(auditLog.getEntityId())
                .ipAddress(auditLog.getIpAddress())
                .timestamp(auditLog.getTimestamp())
                .build();
    }
}