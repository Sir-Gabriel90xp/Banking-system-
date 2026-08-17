import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { TransferService } from '../../../core/services/transfer.service';
import { AccountService } from '../../../core/services/account.service';
import { Account } from '../../../shared/models/account.model';
import { TransferRequest } from '../../../shared/models/transfer.model';
import { MoneyInputDirective } from '../../../shared/directives/money-input.directive';
import { parseMoney } from '../../../shared/utils/money-input.util';

@Component({
  selector: 'app-transfer-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, MoneyInputDirective],
  templateUrl: './transfer-form.html',
  styleUrl: './transfer-fomr.scss'
})
export class TransferForm implements OnInit {
  form: FormGroup;
  loading = signal(false);
  errorMessage = signal('');
  myAccounts = signal<Account[]>([]);

  constructor(
    private fb: FormBuilder,
    private transferService: TransferService,
    private accountService: AccountService,
    private router: Router
  ) {
    this.form = this.fb.group({
      sourceAccount: ['', Validators.required],
      destinationMode: ['own', Validators.required],
      destinationAccount: [''],
      destinationAccountNumber: [''],
      amount: [null, [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.accountService.getMyAccounts().subscribe({
      next: (res) => {
        const activeAccounts = (res.data ?? []).filter((account) => account.status === 'ACTIVE');
        this.myAccounts.set(activeAccounts);
        this.form.patchValue({ sourceAccount: activeAccounts[0]?.id ?? '' });
        this.selectFirstOwnDestination();
      },
      error: () => this.errorMessage.set('No se pudieron cargar tus cuentas.')
    });

    this.form.get('destinationMode')?.valueChanges.subscribe(() => {
      this.errorMessage.set('');
      this.selectFirstOwnDestination();
    });

    this.form.get('sourceAccount')?.valueChanges.subscribe(() => this.selectFirstOwnDestination());
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.errorMessage.set('');

    const raw = this.form.getRawValue();
    const payload: TransferRequest = {
      sourceAccount: raw.sourceAccount,
      amount: parseMoney(raw.amount)
    };

    if (raw.destinationMode === 'own') {
      if (!raw.destinationAccount) {
        this.failLocalValidation('Selecciona una cuenta destino.');
        return;
      }
      payload.destinationAccount = raw.destinationAccount;
    } else {
      const accountNumber = String(raw.destinationAccountNumber ?? '').trim();
      if (!accountNumber) {
        this.failLocalValidation('Escribe el numero de cuenta destino.');
        return;
      }
      payload.destinationAccountNumber = accountNumber;
    }

    this.transferService.create(payload).subscribe({
      next: () => this.router.navigate(['/transfers']),
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Ocurrio un error al realizar la transferencia.');
      }
    });
  }

  destinationAccounts(): Account[] {
    const sourceAccount = this.form.get('sourceAccount')?.value;
    return this.myAccounts().filter((account) => account.id !== sourceAccount);
  }

  isOwnDestination(): boolean {
    return this.form.get('destinationMode')?.value === 'own';
  }

  private selectFirstOwnDestination(): void {
    if (!this.isOwnDestination()) {
      this.form.patchValue({ destinationAccount: '' }, { emitEvent: false });
      return;
    }

    const currentDestination = this.form.get('destinationAccount')?.value;
    const available = this.destinationAccounts();
    const nextDestination = available.some((account) => account.id === currentDestination)
      ? currentDestination
      : available[0]?.id ?? '';

    this.form.patchValue({ destinationAccount: nextDestination }, { emitEvent: false });
  }

  private failLocalValidation(message: string): void {
    this.loading.set(false);
    this.errorMessage.set(message);
  }
}
