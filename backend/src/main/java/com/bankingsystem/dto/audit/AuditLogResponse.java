package com.bankingsystem.dto.audit;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    private UUID id;
    private UUID userId;
    private String username;
    private String action;
    private String entity;
    private UUID entityId;
    private String ipAddress;
    private Instant timestamp;
}