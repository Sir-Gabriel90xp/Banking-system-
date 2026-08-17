// src/app/features/accounts/account-form/account-form.ts
import { Component, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AccountService } from '../../../core/services/account.service';
import { AuthService } from '../../../core/services/auth.service';
import { CustomerService } from '../../../core/services/customer.service';
import { AccountRequest, AccountType } from '../../../shared/models/account.model';
import { Customer } from '../../../shared/models/customer.model';
import { MoneyInputDirective } from '../../../shared/directives/money-input.directive';
import { parseMoney } from '../../../shared/utils/money-input.util';

/**
 * Solo creación: AccountController no expone un PUT /accounts/{id}, así que
 * a diferencia de CustomerForm este componente no tiene modo edición.
 */
@Component({
  selector: 'app-account-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, MoneyInputDirective],
  templateUrl: './account-form.html',
  styleUrl: './account-form.scss'
})
export class AccountForm implements OnInit {
  form: FormGroup;
  loading = signal(false);
  errorMessage = signal('');
  customers = signal<Customer[]>([]);
  isStaff = computed(() => {
    const role = this.authService.currentUser()?.role;
    return role === 'ROLE_ADMIN' || role === 'ROLE_EMPLOYEE';
  });
  isCustomer = computed(() => this.authService.currentUser()?.role === 'ROLE_CUSTOMER');
  accountTypes: { value: AccountType; label: string }[] = [
    { value: 'SAVINGS', label: 'Ahorro' },
    { value: 'CHECKING', label: 'Corriente' }
  ];

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private customerService: CustomerService,
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      customerId: ['', Validators.required],
      accountType: ['SAVINGS', Validators.required],
      initialBalance: [0, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.configureRoleMode();

    if (this.isStaff()) {
      // Reutiliza CustomerService (ya confirmado) para poblar el selector de
      // cliente en vez de pedir un UUID a mano.
      this.customerService.list('', 0, 100).subscribe({
        next: (res) => this.customers.set(res.data.content),
        error: () => this.errorMessage.set('No se pudieron cargar los clientes para el selector.')
      });
    }
  }

  submit(): void {
    if (this.form.invalid) return;
    this.loading.set(true);
    this.errorMessage.set('');

    const raw = this.form.getRawValue();
    const payload: AccountRequest = {
      accountType: raw.accountType,
      initialBalance: this.isCustomer() ? 0 : parseMoney(raw.initialBalance)
    };

    if (this.isStaff()) {
      payload.customerId = raw.customerId;
    }

    this.accountService.create(payload).subscribe({
      next: () => this.router.navigate(['/accounts']),
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Ocurrió un error al crear la cuenta.');
      }
    });
  }

  private configureRoleMode(): void {
    const customerControl = this.form.get('customerId');
    const initialBalanceControl = this.form.get('initialBalance');

    if (this.isCustomer()) {
      customerControl?.clearValidators();
      customerControl?.setValue('');
      initialBalanceControl?.clearValidators();
      initialBalanceControl?.setValue(0);
    } else {
      customerControl?.setValidators([Validators.required]);
      initialBalanceControl?.setValidators([Validators.required, Validators.min(0)]);
    }

    customerControl?.updateValueAndValidity({ emitEvent: false });
    initialBalanceControl?.updateValueAndValidity({ emitEvent: false });
  }
}
