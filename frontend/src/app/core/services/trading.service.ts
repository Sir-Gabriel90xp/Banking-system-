import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import {
  TradingCashRequest,
  TradingOrderPage,
  TradingOrderRequest,
  TradingWallet
} from '../../shared/models/trading.model';

@Injectable({ providedIn: 'root' })
export class TradingService {
  private readonly tradingUrl = `${environment.apiUrl}/trading`;

  constructor(private http: HttpClient) {}

  portfolio(): Observable<ApiResponse<TradingWallet>> {
    return this.http.get<ApiResponse<TradingWallet>>(`${this.tradingUrl}/portfolio`);
  }

  deposit(request: TradingCashRequest): Observable<ApiResponse<TradingWallet>> {
    return this.http.post<ApiResponse<TradingWallet>>(`${this.tradingUrl}/deposit`, request);
  }

  withdraw(request: TradingCashRequest): Observable<ApiResponse<TradingWallet>> {
    return this.http.post<ApiResponse<TradingWallet>>(`${this.tradingUrl}/withdraw`, request);
  }

  buy(request: TradingOrderRequest): Observable<ApiResponse<TradingWallet>> {
    return this.http.post<ApiResponse<TradingWallet>>(`${this.tradingUrl}/buy`, request);
  }

  sell(request: TradingOrderRequest): Observable<ApiResponse<TradingWallet>> {
    return this.http.post<ApiResponse<TradingWallet>>(`${this.tradingUrl}/sell`, request);
  }

  orders(page = 0, size = 10): Observable<ApiResponse<TradingOrderPage>> {
    const params = new HttpParams()
      .set('page', `${page}`)
      .set('size', `${size}`)
      .set('sort', 'createdAt,desc');

    return this.http.get<ApiResponse<TradingOrderPage>>(`${this.tradingUrl}/orders`, { params });
  }
}
