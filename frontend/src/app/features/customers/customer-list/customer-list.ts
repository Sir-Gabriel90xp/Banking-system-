// src/app/features/customers/customer-list/customer-list.ts
//
// MODIFICADO respecto al archivo original: se agregó el import de
// avatarUrl() y el método avatarFor() para pintar un avatar con iniciales
// (UI Avatars) al lado del nombre en la tabla. El resto del archivo queda
// exactamente igual al que ya está corriendo.
import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../../core/services/customer.service';
import { Customer } from '../../../shared/models/customer.model';
import { avatarUrl } from '../../../shared/utils/avatar.util';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './customer-list.html',
  styleUrl: './customer-list.scss'
})
export class CustomerList implements OnInit {
  customers = signal<Customer[]>([]);
  loading = signal(true);
  errorMessage = signal('');
  searchTerm = '';
  currentPage = signal(0);
  totalPages = signal(0);

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch(): void {
    this.loading.set(true);
    this.customerService.list(this.searchTerm, this.currentPage()).subscribe({
      next: (res) => {
        this.customers.set(res.data.content);
        this.totalPages.set(res.data.totalPages);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar los clientes.');
        this.loading.set(false);
      }
    });
  }

  onSearch(): void {
    this.currentPage.set(0);
    this.fetch();
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

  /** UI Avatars, iniciales a partir de nombre + apellido. */
  avatarFor(c: Customer): string {
    return avatarUrl(`${c.firstName} ${c.lastName}`, 32);
  }

  remove(id: string, name: string): void {
    if (!confirm(`¿Eliminar al cliente ${name}? Esta acción es reversible (soft delete).`)) return;
    this.customerService.delete(id).subscribe({
      next: () => this.fetch(),
      error: () => alert('No se pudo eliminar el cliente.')
    });
  }
}