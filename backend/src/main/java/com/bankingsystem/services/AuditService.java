package com.bankingsystem.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankingsystem.dto.audit.AuditLogResponse;
import com.bankingsystem.mapper.AuditLogMapper;
import com.bankingsystem.repositories.AuditLogRepository;

import lombok.RequiredArgsConstructor;

/** RF-07: solo lectura, restringido a ROLE_ADMIN a nivel de Controller. */
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Transactional(readOnly = true)
    public Page<AuditLogResponse> search(UUID userId, String entity, Instant from, Instant to, Pageable pageable) {
        return auditLogRepository.search(userId, entity, from, to, pageable)
                .map(auditLogMapper::toResponse);
    }
}