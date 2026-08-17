// src/app/features/loans/loan-form/loan-form.ts
import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoanService } from '../../../core/services/loan.service';
import { LoanRequest } from '../../../shared/models/loan.model';
import { MoneyInputDirective } from '../../../shared/directives/money-input.directive';
import { parseMoney } from '../../../shared/utils/money-input.util';

/**
 * Solo solicitud (CU-08). No hay edición: interestRate y monthlyPayment los
 * calcula el backend con la tasa fija de ADR-009 (0.12 anual vía
 * application.yml) — el form no los pide.
 */
@Component({
  selector: 'app-loan-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, MoneyInputDirective],
  templateUrl: './loan-fomr.html',
  styleUrl: './loan-form.scss'
})
export class LoanForm {
  form: FormGroup;
  loading = signal(false);
  errorMessage = signal('');

  constructor(
    private fb: FormBuilder,
    private loanService: LoanService,
    private router: Router
  ) {
    this.form = this.fb.group({
      amount: [null, [Validators.required, Validators.min(0.01)]],
      termMonths: [null, [Validators.required, Validators.min(1), Validators.max(360)]]
    });
  }

  submit(): void {
    if (this.form.invalid) return;
    this.loading.set(true);
    this.errorMessage.set('');

    const raw = this.form.getRawValue();
    const payload: LoanRequest = {
      amount: parseMoney(raw.amount),
      termMonths: Number(raw.termMonths)
    };

    this.loanService.requestLoan(payload).subscribe({
      next: () => this.router.navigate(['/loans']),
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Ocurrió un error al solicitar el préstamo.');
      }
    });
  }
}
