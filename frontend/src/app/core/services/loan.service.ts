// src/app/core/services/loan.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import { Loan, LoanRequest, LoanStatus } from '../../shared/models/loan.model';

interface LoanPage {
  content: Loan[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class LoanService {
  private readonly loansUrl = `${environment.apiUrl}/loans`;

  constructor(private http: HttpClient) {}

  /** POST /loans — solo CUSTOMER puede llamarlo (@PreAuthorize en el backend). */
  requestLoan(request: LoanRequest): Observable<ApiResponse<Loan>> {
    return this.http.post<ApiResponse<Loan>>(this.loansUrl, request);
  }

  /**
   * GET /loans — abierto a cualquier autenticado; el backend restringe por
   * propiedad para CUSTOMER (LoanService.list(), según AI_HANDOFF.md).
   * customerId y status son filtros opcionales, útiles para ADMIN/EMPLOYEE.
   */
  list(
    customerId?: string,
    status?: LoanStatus,
    page = 0,
    size = 10
  ): Observable<ApiResponse<LoanPage>> {
    let params = new HttpParams().set('page', page).set('size', size);
    if (customerId) params = params.set('customerId', customerId);
    if (status) params = params.set('status', status);

    return this.http.get<ApiResponse<LoanPage>>(this.loansUrl, { params });
  }

  getById(id: string): Observable<ApiResponse<Loan>> {
    return this.http.get<ApiResponse<Loan>>(`${this.loansUrl}/${id}`);
  }

  /** PATCH /loans/{id}/approve — ADMIN/EMPLOYEE únicamente. */
  approve(id: string): Observable<ApiResponse<Loan>> {
    return this.http.patch<ApiResponse<Loan>>(`${this.loansUrl}/${id}/approve`, {});
  }

  /** PATCH /loans/{id}/reject — ADMIN/EMPLOYEE únicamente. */
  reject(id: string): Observable<ApiResponse<Loan>> {
    return this.http.patch<ApiResponse<Loan>>(`${this.loansUrl}/${id}/reject`, {});
  }
}