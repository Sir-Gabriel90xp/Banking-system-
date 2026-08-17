// src/app/features/loans/loan-list/loan-list.ts
import { Component, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { LoanService } from '../../../core/services/loan.service';
import { AuthService } from '../../../core/services/auth.service';
import { Loan } from '../../../shared/models/loan.model';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './loan-list.html',
  styleUrl: './loan-list.scss'
})
export class LoanList implements OnInit {
  loans = signal<Loan[]>([]);
  loading = signal(true);
  errorMessage = signal('');
  currentPage = signal(0);
  totalPages = signal(0);

  /** Solo ADMIN/EMPLOYEE puede aprobar/rechazar (@PreAuthorize en LoanController). */
  isStaff = computed(() => {
    const role = this.authService.currentUser()?.role;
    return role === 'ROLE_ADMIN' || role === 'ROLE_EMPLOYEE';
  });

  /** Solo CUSTOMER puede solicitar préstamos (hasRole('CUSTOMER') en el backend). */
  isCustomer = computed(() => this.authService.currentUser()?.role === 'ROLE_CUSTOMER');

  constructor(
    private loanService: LoanService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch(): void {
    this.loading.set(true);
    this.loanService.list(undefined, undefined, this.currentPage()).subscribe({
      next: (res) => {
        this.loans.set(res.data.content);
        this.totalPages.set(res.data.totalPages);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar los préstamos.');
        this.loading.set(false);
      }
    });
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

  approve(id: string): void {
    if (!confirm('¿Aprobar este préstamo?')) return;
    this.loanService.approve(id).subscribe({
      next: () => this.fetch(),
      error: () => alert('No se pudo aprobar el préstamo.')
    });
  }

  reject(id: string): void {
    if (!confirm('¿Rechazar este préstamo?')) return;
    this.loanService.reject(id).subscribe({
      next: () => this.fetch(),
      error: () => alert('No se pudo rechazar el préstamo.')
    });
  }
}