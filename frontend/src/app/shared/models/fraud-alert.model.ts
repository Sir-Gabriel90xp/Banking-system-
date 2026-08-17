// src/app/shared/models/fraud-alert.model.ts
export type FraudSeverity = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
export type FraudAlertStatus = 'OPEN' | 'RESOLVED';

export interface FraudAlert {
  id: string;
  accountId: string;
  accountNumber: string;
  reason: string;
  severity: FraudSeverity;
  status: FraudAlertStatus;
  detectedAt: string;
}
