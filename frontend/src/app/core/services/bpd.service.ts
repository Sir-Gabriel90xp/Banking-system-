import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import {
  BpdAtmLocation,
  BpdConfirmAccountRequest,
  BpdConfirmAccountResponse
} from '../../shared/models/bpd.model';

@Injectable({ providedIn: 'root' })
export class BpdService {
  private readonly bpdUrl = `${environment.apiUrl}/bpd`;

  constructor(private http: HttpClient) {}

  confirmAccount(
    request: BpdConfirmAccountRequest
  ): Observable<ApiResponse<BpdConfirmAccountResponse>> {
    return this.http.post<ApiResponse<BpdConfirmAccountResponse>>(
      `${this.bpdUrl}/confirm-account`,
      request
    );
  }

  getAtmLocations(page = 0): Observable<ApiResponse<BpdAtmLocation[]>> {
    const params = new HttpParams().set('page', page);
    return this.http.get<ApiResponse<BpdAtmLocation[]>>(`${this.bpdUrl}/atm-locations`, { params });
  }
}
