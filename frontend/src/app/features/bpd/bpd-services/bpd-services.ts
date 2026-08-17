import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BpdService } from '../../../core/services/bpd.service';
import {
  BpdAtmLocation,
  BpdConfirmAccountRequest,
  BpdConfirmAccountResponse,
  BpdDocumentType
} from '../../../shared/models/bpd.model';

@Component({
  selector: 'app-bpd-services',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bpd-services.html',
  styleUrl: './bpd-services.scss'
})
export class BpdServices implements OnInit {
  documentTypes: BpdDocumentType[] = ['CEDULA', 'RNC'];

  confirmRequest: BpdConfirmAccountRequest = {
    documentType: 'CEDULA',
    documentNumber: '',
    accountNumber: ''
  };

  confirmResult = signal<BpdConfirmAccountResponse | null>(null);
  confirmError = signal('');
  confirming = signal(false);

  atmLocations = signal<BpdAtmLocation[]>([]);
  atmError = signal('');
  loadingAtms = signal(false);
  atmPage = signal(0);

  constructor(private bpdService: BpdService) {}

  ngOnInit(): void {
    this.loadAtms();
  }

  confirmAccount(): void {
    this.confirmError.set('');
    this.confirmResult.set(null);
    this.confirming.set(true);

    this.bpdService.confirmAccount(this.confirmRequest).subscribe({
      next: (response) => {
        this.confirmResult.set(response.data);
        this.confirming.set(false);
      },
      error: (error) => {
        this.confirmError.set(error?.error?.message ?? 'No se pudo confirmar la cuenta.');
        this.confirming.set(false);
      }
    });
  }

  loadAtms(): void {
    this.atmError.set('');
    this.loadingAtms.set(true);

    this.bpdService.getAtmLocations(this.atmPage()).subscribe({
      next: (response) => {
        this.atmLocations.set(response.data ?? []);
        this.loadingAtms.set(false);
      },
      error: (error) => {
        this.atmLocations.set([]);
        this.atmError.set(error?.error?.message ?? 'No se pudieron cargar los cajeros.');
        this.loadingAtms.set(false);
      }
    });
  }

  nextAtmPage(): void {
    if (this.atmPage() < 99) {
      this.atmPage.update((page) => page + 1);
      this.loadAtms();
    }
  }

  prevAtmPage(): void {
    if (this.atmPage() > 0) {
      this.atmPage.update((page) => page - 1);
      this.loadAtms();
    }
  }

  mapsUrl(location: BpdAtmLocation): string | null {
    if (location.latitude === null || location.longitude === null) {
      return null;
    }
    return `https://www.google.com/maps?q=${location.latitude},${location.longitude}`;
  }
}
