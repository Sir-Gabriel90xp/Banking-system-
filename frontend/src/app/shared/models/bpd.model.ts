export type BpdDocumentType = 'CEDULA' | 'RNC';

export interface BpdConfirmAccountRequest {
  documentType: BpdDocumentType;
  documentNumber: string;
  accountNumber: string;
}

export interface BpdConfirmAccountResponse {
  status: boolean;
  message: string;
}

export interface BpdAtmLocation {
  id: string;
  name: string;
  category: string;
  address: string;
  schedule: string;
  latitude: number | null;
  longitude: number | null;
}
