// src/app/core/services/account.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import {
  Account,
  AccountRequest,
  AccountStatus,
  BalanceResponse,
} from '../../shared/models/account.model';

interface AccountPage {
  content: Account[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class AccountService {
  private readonly accountsUrl = `${environment.apiUrl}/accounts`;

  constructor(private http: HttpClient) {}

  /**
   * GET /accounts — corregido: el backend devuelve Page<AccountResponse>
   * (Spring Data), no un array plano. Mismo patrón que CustomerService.list().
   * status y customerId son filtros opcionales según AccountController.listAccounts().
   */
  list(
    status?: AccountStatus,
    customerId?: string,
    page = 0,
    size = 10
  ): Observable<ApiResponse<AccountPage>> {
    let params = new HttpParams().set('page', page).set('size', size);
    if (status) params = params.set('status', status);
    if (customerId) params = params.set('customerId', customerId);

    return this.http.get<ApiResponse<AccountPage>>(this.accountsUrl, { params });
  }

  /**
   * Conveniencia para el Dashboard, que solo necesita la lista plana de
   * cuentas del usuario autenticado. Antes llamaba directo a GET /accounts
   * asumiendo un array; ahora reutiliza list() y aplana el content.
   */
  getMyAccounts(): Observable<ApiResponse<Account[]>> {
    return this.list().pipe(
      map((response) => ({ ...response, data: response.data?.content ?? [] }))
    );
  }

  getById(id: string): Observable<ApiResponse<Account>> {
    return this.http.get<ApiResponse<Account>>(`${this.accountsUrl}/${id}`);
  }

  getBalance(id: string): Observable<ApiResponse<BalanceResponse>> {
    return this.http.get<ApiResponse<BalanceResponse>>(`${this.accountsUrl}/${id}/balance`);
  }

  /** ADMIN/EMPLOYEE únicamente — el backend ya lo restringe con @PreAuthorize. */
  create(request: AccountRequest): Observable<ApiResponse<Account>> {
    return this.http.post<ApiResponse<Account>>(this.accountsUrl, request);
  }

  /** ADMIN/EMPLOYEE únicamente. */
  block(id: string): Observable<ApiResponse<Account>> {
    return this.http.patch<ApiResponse<Account>>(`${this.accountsUrl}/${id}/block`, {});
  }

  /** ADMIN/EMPLOYEE únicamente. */
  activate(id: string): Observable<ApiResponse<Account>> {
    return this.http.patch<ApiResponse<Account>>(`${this.accountsUrl}/${id}/activate`, {});
  }

  // Nota: no hay delete() — DELETE /accounts/{id} sigue sin implementar en
  // el backend (comentario explícito en AccountController.java).
}