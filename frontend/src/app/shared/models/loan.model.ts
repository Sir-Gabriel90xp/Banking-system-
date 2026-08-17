// src/app/shared/models/loan.model.ts
//
// Refleja com.bankingsystem.dto.loan.LoanRequest / LoanResponse.
// LoanStatus confirmado en bitacora.md (Sesión 5): PENDING | APPROVED |
// REJECTED | PAID — no es un supuesto, a diferencia de AccountStatus.
export type LoanStatus = 'PENDING' | 'APPROVED' | 'REJECTED' | 'PAID';

export interface Loan {
  id: string;
  customerId: string;
  amount: number;
  interestRate: number;
  termMonths: number;
  monthlyPayment: number;
  status: LoanStatus;
  createdAt: string;
}

/**
 * CU-08: el customer solicitante se resuelve del usuario autenticado en el
 * backend — no viaja en el body. El frontend solo envía amount y termMonths.
 */
export interface LoanRequest {
  amount: number;
  termMonths: number;
}