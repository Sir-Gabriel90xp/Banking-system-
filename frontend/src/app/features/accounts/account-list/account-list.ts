// src/app/features/accounts/account-list/account-list.ts
import { Component, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AccountService } from '../../../core/services/account.service';
import { AuthService } from '../../../core/services/auth.service';
import { CurrencyService } from '../../../core/services/currency.service';
import { Account } from '../../../shared/models/account.model';

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './account-list.html',
  styleUrl: './account-list.scss'
})
export class AccountList implements OnInit {
  accounts = signal<Account[]>([]);
  loading = signal(true);
  errorMessage = signal('');
  currentPage = signal(0);
  totalPages = signal(0);

  /**
   * GET /accounts es visible para cualquier autenticado, pero crear /
   * bloquear / activar están restringidos a ADMIN y EMPLOYEE en el backend
   * (@PreAuthorize en AccountController). Ocultamos esas acciones en la UI
   * para el resto — el backend sigue siendo la autoridad real.
   */
  isStaff = computed(() => {
    const role = this.authService.currentUser()?.role;
    return role === 'ROLE_ADMIN' || role === 'ROLE_EMPLOYEE';
  });

  canCreateAccount = computed(() => {
    const role = this.authService.currentUser()?.role;
    return role === 'ROLE_ADMIN' || role === 'ROLE_EMPLOYEE' || role === 'ROLE_CUSTOMER';
  });

  /**
   * Saldo convertido a USD por cuenta a traves del backend
   * (/exchange-rates/convert). `undefined` = pendiente; `null` = moneda no
   * soportada o error externo, sin romper la fila.
   */
  usdEquivalents = signal<Record<string, number | null>>({});

  constructor(
    private accountService: AccountService,
    private authService: AuthService,
    private currencyService: CurrencyService
  ) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch(): void {
    this.loading.set(true);
    this.accountService.list(undefined, undefined, this.currentPage()).subscribe({
      next: (res) => {
        this.accounts.set(res.data.content);
        this.totalPages.set(res.data.totalPages);
        this.loading.set(false);
        this.loadUsdEquivalents(res.data.content);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar las cuentas.');
        this.loading.set(false);
      }
    });
  }

  private loadUsdEquivalents(accounts: Account[]): void {
    for (const account of accounts) {
      this.currencyService.convert(account.balance, account.currency, 'USD').subscribe((usd) => {
        this.usdEquivalents.update((map) => ({ ...map, [account.id]: usd }));
      });
    }
  }

  nextPage(): void {
    if (this.currentPage() + 1 < this.totalPages()) {
      this.currentPage.update((p) => p + 1);
      this.fetch();
    }
  }

  prevPage(): void {
    if (this.currentPage() > 0) {
      this.currentPage.update((p) => p - 1);
      this.fetch();
    }
  }

  block(id: string, accountNumber: string): void {
    if (!confirm(`¿Bloquear la cuenta ${accountNumber}?`)) return;
    this.accountService.block(id).subscribe({
      next: () => this.fetch(),
      error: () => alert('No se pudo bloquear la cuenta.')
    });
  }

  activate(id: string, accountNumber: string): void {
    if (!confirm(`¿Activar la cuenta ${accountNumber}?`)) return;
    this.accountService.activate(id).subscribe({
      next: () => this.fetch(),
      error: () => alert('No se pudo activar la cuenta.')
    });
  }
}
