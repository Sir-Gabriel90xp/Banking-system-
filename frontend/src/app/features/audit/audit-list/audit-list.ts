// src/app/features/audit/audit-list/audit-list.ts
import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuditService } from '../../../core/services/audit.service';
import { GeoService } from '../../../core/services/geo.service';
import { AuditLog } from '../../../shared/models/audit-log.model';

/** Solo lectura — AuditController únicamente expone GET /audit (ROLE_ADMIN). */
@Component({
  selector: 'app-audit-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './audit-list.html',
  styleUrl: './audit-list.scss'
})
export class AuditList implements OnInit {
  logs = signal<AuditLog[]>([]);
  loading = signal(true);
  errorMessage = signal('');
  currentPage = signal(0);
  totalPages = signal(0);

  entityFilter = '';
  fromFilter = ''; // <input type="date"> -> 'YYYY-MM-DD'
  toFilter = '';

  /**
   * País resuelto por IP (ipwho.is), keyed por ipAddress. `undefined` =
   * todavía no se resolvió; `null` = IP interna/local o no se pudo
   * geolocalizar. Útil para detectar accesos desde ubicaciones inusuales —
   * insumo directo para cuando se implemente el módulo de Fraude.
   */
  countries = signal<Record<string, string | null>>({});

  constructor(
    private auditService: AuditService,
    private geoService: GeoService
  ) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch(): void {
    this.loading.set(true);
    const from = this.fromFilter ? `${this.fromFilter}T00:00:00Z` : undefined;
    const to = this.toFilter ? `${this.toFilter}T23:59:59Z` : undefined;

    this.auditService
      .list(undefined, this.entityFilter || undefined, from, to, this.currentPage())
      .subscribe({
        next: (res) => {
          this.logs.set(res.data.content);
          this.totalPages.set(res.data.totalPages);
          this.loading.set(false);
          this.loadCountries(res.data.content);
        },
        error: () => {
          this.errorMessage.set('No se pudo cargar el registro de auditoría.');
          this.loading.set(false);
        }
      });
  }

  private loadCountries(logs: AuditLog[]): void {
    // Deduplicar IPs repetidas en la misma página para no pegarle a
    // ipwho.is más veces de las necesarias (el servicio ya cachea por IP,
    // pero esto evita disparar el mismo request en paralelo varias veces).
    const uniqueIps = [...new Set(logs.map((l) => l.ipAddress))];
    for (const ip of uniqueIps) {
      this.geoService.lookupCountry(ip).subscribe((country) => {
        this.countries.update((map) => ({ ...map, [ip]: country }));
      });
    }
  }

  onFilter(): void {
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
}