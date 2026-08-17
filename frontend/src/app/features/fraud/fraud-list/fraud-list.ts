// src/app/features/fraud/fraud-list/fraud-list.ts
import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FraudAlertService } from '../../../core/services/fraud-alert.service';
import { FraudAlert } from '../../../shared/models/fraud-alert.model';

@Component({
  selector: 'app-fraud-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './fraud-list.html',
  styleUrl: './fraud-list.scss'
})
export class FraudList implements OnInit {
  alerts = signal<FraudAlert[]>([]);
  loading = signal(true);
  resolvingId = signal<string | null>(null);
  errorMessage = signal('');

  constructor(private fraudAlertService: FraudAlertService) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    this.fraudAlertService.list().subscribe({
      next: (res) => {
        this.alerts.set(res.data ?? []);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar las alertas de fraude.');
        this.loading.set(false);
      }
    });
  }

  resolve(alert: FraudAlert): void {
    if (alert.status === 'RESOLVED') return;
    if (!confirm(`Resolver alerta de la cuenta ${alert.accountNumber}?`)) return;

    this.resolvingId.set(alert.id);
    this.fraudAlertService.resolve(alert.id).subscribe({
      next: (res) => {
        this.alerts.update((items) => items.map((item) => item.id === alert.id ? res.data : item));
        this.resolvingId.set(null);
      },
      error: () => {
        this.errorMessage.set('No se pudo resolver la alerta.');
        this.resolvingId.set(null);
      }
    });
  }
}
