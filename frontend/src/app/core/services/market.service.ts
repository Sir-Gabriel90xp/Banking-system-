import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import {
  MarketBasicFinancials,
  MarketCompanyProfile,
  MarketHoliday,
  MarketNews,
  MarketQuote,
  MarketRecommendationTrend,
  MarketStatus,
  MarketSymbolSearch
} from '../../shared/models/market.model';

@Injectable({ providedIn: 'root' })
export class MarketService {
  private readonly marketsUrl = `${environment.apiUrl}/markets`;

  constructor(private http: HttpClient) {}

  searchSymbols(q: string, exchange?: string): Observable<ApiResponse<MarketSymbolSearch>> {
    let params = new HttpParams().set('q', q);
    if (exchange) params = params.set('exchange', exchange);
    return this.http.get<ApiResponse<MarketSymbolSearch>>(`${this.marketsUrl}/symbols/search`, { params });
  }

  getQuote(symbol: string): Observable<ApiResponse<MarketQuote>> {
    const params = new HttpParams().set('symbol', symbol);
    return this.http.get<ApiResponse<MarketQuote>>(`${this.marketsUrl}/quote`, { params });
  }

  getCompanyProfile(symbol: string): Observable<ApiResponse<MarketCompanyProfile>> {
    const params = new HttpParams().set('symbol', symbol);
    return this.http.get<ApiResponse<MarketCompanyProfile>>(`${this.marketsUrl}/company-profile`, { params });
  }

  getMarketStatus(exchange = 'US'): Observable<ApiResponse<MarketStatus>> {
    const params = new HttpParams().set('exchange', exchange);
    return this.http.get<ApiResponse<MarketStatus>>(`${this.marketsUrl}/status`, { params });
  }

  getMarketHolidays(exchange = 'US'): Observable<ApiResponse<MarketHoliday>> {
    const params = new HttpParams().set('exchange', exchange);
    return this.http.get<ApiResponse<MarketHoliday>>(`${this.marketsUrl}/holidays`, { params });
  }

  getMarketNews(category = 'general'): Observable<ApiResponse<MarketNews[]>> {
    const params = new HttpParams().set('category', category);
    return this.http.get<ApiResponse<MarketNews[]>>(`${this.marketsUrl}/news`, { params });
  }

  getCompanyNews(symbol: string, from: string, to: string): Observable<ApiResponse<MarketNews[]>> {
    const params = new HttpParams().set('symbol', symbol).set('from', from).set('to', to);
    return this.http.get<ApiResponse<MarketNews[]>>(`${this.marketsUrl}/company-news`, { params });
  }

  getBasicFinancials(symbol: string): Observable<ApiResponse<MarketBasicFinancials>> {
    const params = new HttpParams().set('symbol', symbol);
    return this.http.get<ApiResponse<MarketBasicFinancials>>(`${this.marketsUrl}/basic-financials`, { params });
  }

  getRecommendations(symbol: string): Observable<ApiResponse<MarketRecommendationTrend[]>> {
    const params = new HttpParams().set('symbol', symbol);
    return this.http.get<ApiResponse<MarketRecommendationTrend[]>>(`${this.marketsUrl}/recommendations`, { params });
  }
}
