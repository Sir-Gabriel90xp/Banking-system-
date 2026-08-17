// src/app/features/dashboard/dashboard.ts
import { Component, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { timeout } from 'rxjs';
import { AccountService } from '../../core/services/account.service';
import { AuthService } from '../../core/services/auth.service';
import { Account } from '../../shared/models/account.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class Dashboard implements OnInit {
  accounts = signal<Account[]>([]);
  loading = signal(true);
  errorMessage = signal('');
  isStaff = computed(() => {
    const role = this.authService.currentUser()?.role;
    return role === 'ROLE_ADMIN' || role === 'ROLE_EMPLOYEE';
  });

  today = new Date().toLocaleDateString('es-DO', {
    weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'
  });

  constructor(
    private accountService: AccountService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.accountService.getMyAccounts().pipe(timeout(15000)).subscribe({
      next: (res) => {
        this.accounts.set(res.data ?? []);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar tus cuentas. Verifica el endpoint /accounts.');
        this.loading.set(false);
      }
    });
  }

  get totalBalance(): number {
    return this.accounts().reduce((sum, a) => sum + Number(a.balance ?? 0), 0);
  }

  formatMoney(value: number): string {
    return new Intl.NumberFormat('es-DO', { minimumFractionDigits: 2 }).format(value);
  }
}
