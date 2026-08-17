// src/app/shared/models/account.model.ts
//
// Refleja los DTOs reales:
// - com.bankingsystem.dto.account.AccountResponse
// - com.bankingsystem.dto.account.AccountRequest
// - com.bankingsystem.dto.account.BalanceResponse
export type AccountType = 'SAVINGS' | 'CHECKING';
export type AccountStatus = 'ACTIVE' | 'BLOCKED' | 'CLOSED';

export interface Account {
  id: string;
  accountNumber: string;
  accountType: AccountType;
  balance: number;
  currency: string;
  status: AccountStatus;
  customerId: string;
  createdAt: string;
}

export interface AccountRequest {
  customerId?: string | null;
  accountType: AccountType;
  initialBalance?: number | null;
}

export interface BalanceResponse {
  accountId: string;
  accountNumber: string;
  balance: number;
  currency: string;
  asOf: string;
}
