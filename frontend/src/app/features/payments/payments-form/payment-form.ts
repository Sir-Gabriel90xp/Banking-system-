// src/app/features/payments/payment-form/payment-form.ts
import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PaymentService } from '../../../core/services/payment.service';
import { LoanService } from '../../../core/services/loan.service';
import { AccountService } from '../../../core/services/account.service';
import { Account } from '../../../shared/models/account.model';
import { Loan } from '../../../shared/models/loan.model';
import { PaymentRequest } from '../../../shared/models/payment.model';
import { MoneyInputDirective } from '../../../shared/directives/money-input.directive';
import { parseMoney } from '../../../shared/utils/money-input.util';

@Component({
  selector: 'app-payment-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, MoneyInputDirective],
  templateUrl: './payment-form.html',
  styleUrl: './payment-form.scss'
})
export class PaymentForm implements OnInit {
  form: FormGroup;
  loading = signal(false);
  errorMessage = signal('');
  loans = signal<Loan[]>([]);
  accounts = signal<Account[]>([]);

  constructor(
    private fb: FormBuilder,
    private paymentService: PaymentService,
    private loanService: LoanService,
    private accountService: AccountService,
    private router: Router
  ) {
    this.form = this.fb.group({
      loanId: ['', Validators.required],
      accountId: ['', Validators.required],
      amount: [null, [Validators.required, Validators.min(0.01)]],
      paymentMethod: ['ACCOUNT_DEBIT', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loanService.list(undefined, 'APPROVED').subscribe({
      next: (res) => this.loans.set(res.data.content),
      error: () => this.errorMessage.set('No se pudieron cargar los prestamos aprobados.')
    });

    this.accountService.getMyAccounts().subscribe({
      next: (res) => this.accounts.set((res.data ?? []).filter((account) => account.status === 'ACTIVE')),
      error: () => this.errorMessage.set('No se pudieron cargar las cuentas disponibles.')
    });

    this.form.get('loanId')?.valueChanges.subscribe((loanId) => {
      const loan = this.loans().find((item) => item.id === loanId);
      if (!loan) return;

      this.form.patchValue({ amount: loan.monthlyPayment });
      const accountId = this.form.get('accountId')?.value;
      if (accountId && !this.filteredAccounts().some((account) => account.id === accountId)) {
        this.form.patchValue({ accountId: '' }, { emitEvent: false });
      }
    });
  }

  filteredAccounts(): Account[] {
    const loanId = this.form.get('loanId')?.value;
    const loan = this.loans().find((item) => item.id === loanId);

    if (!loan) {
      return this.accounts();
    }

    return this.accounts().filter((account) => account.customerId === loan.customerId);
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.errorMessage.set('');

    const raw = this.form.getRawValue();
    const payload: PaymentRequest = {
      loanId: raw.loanId,
      accountId: raw.accountId,
      amount: parseMoney(raw.amount),
      paymentMethod: raw.paymentMethod
    };

    this.paymentService.registerPayment(payload).subscribe({
      next: () => this.router.navigate(['/payments']),
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Ocurrio un error al registrar el pago.');
      }
    });
  }
}
