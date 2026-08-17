// src/app/shared/models/customer.model.ts
export interface Customer {
  id: string;
  firstName: string;
  lastName: string;
  documentNumber: string;
  birthDate: string | null;
  phone: string | null;
  email: string;
  address: string | null;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface CustomerRequest {
  firstName: string;
  lastName: string;
  documentNumber: string;
  birthDate: string | null;
  phone: string | null;
  email: string;
  address: string | null;
  createLoginUser?: boolean;
  username?: string | null;
  password?: string | null;
  createInitialAccount?: boolean;
  initialAccountType?: 'SAVINGS' | 'CHECKING' | null;
  initialBalance?: number | null;
}
