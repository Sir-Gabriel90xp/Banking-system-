// src/app/features/transfers/transfer-list/transfer-list.ts
import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TransferService } from '../../../core/services/transfer.service';
import { Transfer } from '../../../shared/models/transfer.model';

/**
 * Solo lectura: TransferController únicamente expone create() y
 * getHistory(), no hay edición ni cancelación de transferencias.
 */
@Component({
  selector: 'app-transfer-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './transfer-list.html',
  styleUrl: './transfer-list.scss'
})
export class TransferList implements OnInit {
  transfers = signal<Transfer[]>([]);
  loading = signal(true);
  errorMessage = signal('');
  currentPage = signal(0);
  totalPages = signal(0);

  constructor(private transferService: TransferService) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch(): void {
    this.loading.set(true);
    this.transferService.getHistory(undefined, undefined, this.currentPage()).subscribe({
      next: (res) => {
        this.transfers.set(res.data.content);
        this.totalPages.set(res.data.totalPages);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudo cargar el historial de transferencias.');
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