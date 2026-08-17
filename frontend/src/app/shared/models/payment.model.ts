// src/app/shared/models/payment.model.ts
//
// Refleja com.bankingsystem.dto.payment.PaymentRequest / PaymentResponse.
// El registro de pago de prestamo debita una cuenta bancaria real.
export interface Payment {
  id: string;
  loanId: string;
  accountId: string;
  accountNumber: string;
  amount: number;
  paymentDate: string;
  paymentMethod: string;
  status: string;
}

export interface PaymentRequest {
  loanId: string;
  accountId: string;
  amount: number;
  paymentMethod: string;
}
