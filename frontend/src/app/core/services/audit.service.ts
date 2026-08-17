// src/app/core/services/audit.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import { AuditLog } from '../../shared/models/audit-log.model';

interface AuditLogPage {
  content: AuditLog[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

/** Solo lectura, ROLE_ADMIN — ver @PreAuthorize a nivel de clase en AuditController. */
@Injectable({ providedIn: 'root' })
export class AuditService {
  private readonly auditUrl = `${environment.apiUrl}/audit`;

  constructor(private http: HttpClient) {}

  list(
    userId?: string,
    entity?: string,
    from?: string, // ISO 8601 (Instant) — ej: '2026-08-01T00:00:00Z'
    to?: string,
    page = 0,
    size = 20
  ): Observable<ApiResponse<AuditLogPage>> {
    let params = new HttpParams().set('page', page).set('size', size);
    if (userId) params = params.set('userId', userId);
    if (entity) params = params.set('entity', entity);
    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);

    return this.http.get<ApiResponse<AuditLogPage>>(this.auditUrl, { params });
  }
}