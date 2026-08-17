// src/app/features/payments/payment-list/payment-list.ts
import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PaymentService } from '../../../core/services/payment.service';
import { Payment } from '../../../shared/models/payment.model';

/** Solo lectura: PaymentController únicamente expone registrar y listar. */
@Component({
  selector: 'app-payment-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './payment-list.html',
  styleUrl: './payment-list.scss'
})
export class PaymentList implements OnInit {
  payments = signal<Payment[]>([]);
  loading = signal(true);
  errorMessage = signal('');
  currentPage = signal(0);
  totalPages = signal(0);

  constructor(private paymentService: PaymentService) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch(): void {
    this.loading.set(true);
    this.paymentService.list(undefined, this.currentPage()).subscribe({
      next: (res) => {
        this.payments.set(res.data.content);
        this.totalPages.set(res.data.totalPages);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar los pagos.');
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
}