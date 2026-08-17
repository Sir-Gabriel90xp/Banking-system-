// src/app/core/services/fraud-alert.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import { FraudAlert } from '../../shared/models/fraud-alert.model';

@Injectable({ providedIn: 'root' })
export class FraudAlertService {
  private readonly fraudAlertsUrl = `${environment.apiUrl}/fraud-alerts`;

  constructor(private http: HttpClient) {}

  list(): Observable<ApiResponse<FraudAlert[]>> {
    return this.http.get<ApiResponse<FraudAlert[]>>(this.fraudAlertsUrl);
  }

  resolve(id: string): Observable<ApiResponse<FraudAlert>> {
    return this.http.patch<ApiResponse<FraudAlert>>(`${this.fraudAlertsUrl}/${id}/resolve`, {});
  }
}
