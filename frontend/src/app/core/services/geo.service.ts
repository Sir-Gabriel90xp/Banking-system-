// src/app/core/services/geo.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, shareReplay } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

interface IpWhoIsResponse {
  success: boolean;
  message?: string;
  country: string;
  flag?: { emoji: string };
}

/**
 * ipwho.is (ipwhois.io) — geolocalización de IP. Sin API key, HTTPS,
 * gratis hasta 1000 requests/día.
 *
 * Se eligió en vez de ip-api.com porque el free tier de ip-api.com es
 * HTTP-only (sin TLS) y una app Angular servida por HTTPS lo bloquea por
 * mixed-content — no es una preferencia de estilo, es la única de las dos
 * que efectivamente funciona desde el navegador.
 */
@Injectable({ providedIn: 'root' })
export class GeoService {
  private cache = new Map<string, Observable<string | null>>();

  constructor(private http: HttpClient) {}

  /** País (+ emoji de bandera) para una IP, o null si no se pudo resolver. */
  lookupCountry(ip: string): Observable<string | null> {
    if (this.isPrivateOrLocal(ip)) {
      return of(null); // ipwho.is no puede geolocalizar IPs internas/de desarrollo
    }

    if (!this.cache.has(ip)) {
      const req$ = this.http.get<IpWhoIsResponse>(`https://ipwho.is/${ip}`).pipe(
        map((res) => (res.success ? `${res.flag?.emoji ?? ''} ${res.country}`.trim() : null)),
        catchError(() => of(null)),
        shareReplay(1)
      );
      this.cache.set(ip, req$);
    }
    return this.cache.get(ip)!;
  }

  private isPrivateOrLocal(ip: string): boolean {
    if (!ip) return true;
    return (
      ip === '127.0.0.1' ||
      ip === 'localhost' ||
      ip === '::1' ||
      ip.startsWith('192.168.') ||
      ip.startsWith('10.') ||
      ip.startsWith('172.')
    );
  }
}