import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, catchError, map, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import {
  CurrencyInfo,
  ExchangeConversion,
  ExchangeProvider,
  ExchangeRate,
  ExchangeRatesQuery
} from '../../shared/models/exchange-rate.model';

@Injectable({ providedIn: 'root' })
export class CurrencyService {
  private readonly exchangeUrl = `${environment.apiUrl}/exchange-rates`;

  constructor(private http: HttpClient) {}

  getRates(query: ExchangeRatesQuery = {}): Observable<ApiResponse<ExchangeRate[]>> {
    let params = new HttpParams();

    if (query.base) params = params.set('base', query.base);
    if (query.quotes) params = params.set('quotes', query.quotes);
    if (query.date) params = params.set('date', query.date);
    if (query.from) params = params.set('from', query.from);
    if (query.to) params = params.set('to', query.to);
    if (query.group) params = params.set('group', query.group);
    if (query.providers) params = params.set('providers', query.providers);
    if (query.includeProviders) params = params.set('includeProviders', true);

    return this.http.get<ApiResponse<ExchangeRate[]>>(`${this.exchangeUrl}/rates`, { params });
  }

  getRate(
    base: string,
    quote: string,
    date?: string,
    providers?: string
  ): Observable<ApiResponse<ExchangeRate>> {
    let params = new HttpParams();
    if (date) params = params.set('date', date);
    if (providers) params = params.set('providers', providers);

    return this.http.get<ApiResponse<ExchangeRate>>(
      `${this.exchangeUrl}/rate/${base}/${quote}`,
      { params }
    );
  }

  convertDetailed(
    amount: number,
    base: string,
    quote: string,
    date?: string,
    providers?: string
  ): Observable<ApiResponse<ExchangeConversion>> {
    let params = new HttpParams()
      .set('amount', amount)
      .set('base', base)
      .set('quote', quote);

    if (date) params = params.set('date', date);
    if (providers) params = params.set('providers', providers);

    return this.http.get<ApiResponse<ExchangeConversion>>(`${this.exchangeUrl}/convert`, { params });
  }

  convert(amount: number, base: string, quote: string): Observable<number | null> {
    const normalizedBase = this.normalizeCurrency(base);
    const normalizedQuote = this.normalizeCurrency(quote);

    if (!Number.isFinite(amount) || amount < 0 || !normalizedBase || !normalizedQuote) {
      return of(null);
    }

    if (normalizedBase === normalizedQuote) {
      return of(amount);
    }

    return this.convertDetailed(amount, normalizedBase, normalizedQuote).pipe(
      map((response) => response.data.convertedAmount),
      catchError(() => of(null))
    );
  }

  getCurrencies(scope?: 'all'): Observable<ApiResponse<CurrencyInfo[]>> {
    let params = new HttpParams();
    if (scope) params = params.set('scope', scope);

    return this.http.get<ApiResponse<CurrencyInfo[]>>(`${this.exchangeUrl}/currencies`, { params });
  }

  getCurrency(code: string): Observable<ApiResponse<CurrencyInfo>> {
    return this.http.get<ApiResponse<CurrencyInfo>>(`${this.exchangeUrl}/currencies/${code}`);
  }

  getProviders(): Observable<ApiResponse<ExchangeProvider[]>> {
    return this.http.get<ApiResponse<ExchangeProvider[]>>(`${this.exchangeUrl}/providers`);
  }

  private normalizeCurrency(value: string | null | undefined): string | null {
    if (!value) return null;
    const normalized = value.trim().toUpperCase();
    return /^[A-Z]{3}$/.test(normalized) ? normalized : null;
  }
}
