// src/app/shared/models/transfer.model.ts
//
// Refleja com.bankingsystem.dto.account.transfer.TransferRequest y
// TransferResponse (backend, confirmados en código real).
export interface Transfer {
  id: string;
  sourceAccount: string; // UUID de la cuenta origen
  sourceAccountNumber?: string;
  destinationAccount: string; // UUID de la cuenta destino
  destinationAccountNumber?: string;
  amount: number;
  status: string;
  createdAt: string;
}

export interface TransferRequest {
  sourceAccount: string;
  destinationAccount?: string | null;
  destinationAccountNumber?: string | null;
  amount: number;
}
