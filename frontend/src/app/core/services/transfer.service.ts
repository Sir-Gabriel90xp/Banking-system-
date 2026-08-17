// src/app/core/services/transfer.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import { Transfer, TransferRequest } from '../../shared/models/transfer.model';

interface TransferPage {
  content: Transfer[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class TransferService {
  private readonly transfersUrl = `${environment.apiUrl}/transfers`;

  constructor(private http: HttpClient) {}

  /** POST /transfers */
  create(request: TransferRequest): Observable<ApiResponse<Transfer>> {
    return this.http.post<ApiResponse<Transfer>>(this.transfersUrl, request);
  }

  /**
   * GET /transfers/history — accountId y status son filtros opcionales
   * según TransferController.getHistory().
   */
  getHistory(
    accountId?: string,
    status?: string,
    page = 0,
    size = 10
  ): Observable<ApiResponse<TransferPage>> {
    let params = new HttpParams().set('page', page).set('size', size);
    if (accountId) params = params.set('accountId', accountId);
    if (status) params = params.set('status', status);

    return this.http.get<ApiResponse<TransferPage>>(`${this.transfersUrl}/history`, { params });
  }
}