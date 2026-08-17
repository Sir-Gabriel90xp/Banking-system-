import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of, tap, timeout } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../../shared/models/api-response.model';
import { AuthResponse, LoginRequest, UserProfile } from '../../shared/models/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private accessToken: string | null = localStorage.getItem('accessToken');
  private refreshToken: string | null = localStorage.getItem('refreshToken');
  private readonly requestTimeoutMs = 15000;
  currentUser = signal<UserProfile | null>(null);

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: LoginRequest): Observable<ApiResponse<AuthResponse>> {
    return this.http
      .post<ApiResponse<AuthResponse>>(`${environment.apiUrl}/auth/login`, credentials)
      .pipe(
        timeout(this.requestTimeoutMs),
        tap((res) => {
          if (!res.data?.token || !res.data?.refreshToken) {
            throw new Error('La respuesta de login no incluyo tokens validos.');
          }

          this.setSession(res.data.token, res.data.refreshToken);
          this.setCurrentUserFromToken(res.data.token);
          this.refreshProfileInBackground();
        })
      );
  }

  loadProfile(): Observable<ApiResponse<UserProfile>> {
    return this.http
      .get<ApiResponse<UserProfile>>(`${environment.apiUrl}/auth/me`)
      .pipe(
        timeout(this.requestTimeoutMs),
        tap((res) => this.currentUser.set(res.data))
      );
  }

  ensureAuthenticated(): Observable<boolean> {
    if (!this.accessToken) {
      this.clearSession();
      return of(false);
    }

    if (this.currentUser()) {
      return of(true);
    }

    const restored = this.setCurrentUserFromToken(this.accessToken);
    if (!restored) {
      this.clearSession();
      return of(false);
    }

    this.refreshProfileInBackground();
    return of(true);
  }

  logout(): void {
    this.clearSession();
    this.router.navigate(['/login']);
  }

  resetSession(): void {
    this.clearSession();
  }

  getAccessToken(): string | null {
    return this.accessToken;
  }

  isAuthenticated(): boolean {
    return !!this.accessToken;
  }

  private setSession(accessToken: string, refreshToken: string): void {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
  }

  private clearSession(): void {
    this.accessToken = null;
    this.refreshToken = null;
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    this.currentUser.set(null);
  }

  private refreshProfileInBackground(): void {
    this.loadProfile().subscribe({ error: () => undefined });
  }

  private setCurrentUserFromToken(token: string): boolean {
    const payload = this.decodeJwtPayload(token);
    const role = payload?.['role'];
    const email = payload?.['email'];
    const id = payload?.['sub'];
    const expiresAt = Number(payload?.['exp'] ?? 0) * 1000;

    if (
      !id ||
      !email ||
      !this.isKnownRole(role) ||
      (expiresAt > 0 && Date.now() >= expiresAt)
    ) {
      return false;
    }

    this.currentUser.set({
      id: String(id),
      username: String(email).split('@')[0] || 'usuario',
      email: String(email),
      role,
      enabled: true
    });

    return true;
  }

  private decodeJwtPayload(token: string): Record<string, unknown> | null {
    try {
      const payload = token.split('.')[1];
      const normalized = payload.replace(/-/g, '+').replace(/_/g, '/');
      const padded = normalized.padEnd(normalized.length + ((4 - normalized.length % 4) % 4), '=');
      return JSON.parse(atob(padded)) as Record<string, unknown>;
    } catch {
      return null;
    }
  }

  private isKnownRole(role: unknown): role is UserProfile['role'] {
    return role === 'ROLE_ADMIN' || role === 'ROLE_EMPLOYEE' || role === 'ROLE_CUSTOMER';
  }
}
