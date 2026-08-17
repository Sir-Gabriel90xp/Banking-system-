// src/app/core/services/payment.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import { Payment, PaymentRequest } from '../../shared/models/payment.model';

interface PaymentPage {
  content: Payment[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class PaymentService {
  private readonly paymentsUrl = `${environment.apiUrl}/payments`;

  constructor(private http: HttpClient) {}

  /** POST /payments — abierto a cualquier autenticado, sin @PreAuthorize. */
  registerPayment(request: PaymentRequest): Observable<ApiResponse<Payment>> {
    return this.http.post<ApiResponse<Payment>>(this.paymentsUrl, request);
  }

  /** GET /payments — loanId es el único filtro opcional según PaymentController. */
  list(loanId?: string, page = 0, size = 10): Observable<ApiResponse<PaymentPage>> {
    let params = new HttpParams().set('page', page).set('size', size);
    if (loanId) params = params.set('loanId', loanId);

    return this.http.get<ApiResponse<PaymentPage>>(this.paymentsUrl, { params });
  }

  getById(id: string): Observable<ApiResponse<Payment>> {
    return this.http.get<ApiResponse<Payment>>(`${this.paymentsUrl}/${id}`);
  }
}