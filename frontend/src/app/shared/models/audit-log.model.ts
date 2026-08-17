// src/app/shared/models/audit-log.model.ts
//
// Refleja com.bankingsystem.dto.audit.AuditLogResponse. Módulo de solo
// lectura (AuditController no expone POST/PATCH/DELETE).
export interface AuditLog {
  id: string;
  userId: string;
  username: string;
  action: string;
  entity: string;
  entityId: string;
  ipAddress: string;
  timestamp: string;
}