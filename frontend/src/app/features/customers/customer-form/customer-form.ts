// src/app/features/customers/customer-form/customer-form.ts
import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../../core/services/customer.service';
import { CustomerRequest } from '../../../shared/models/customer.model';
import { MoneyInputDirective } from '../../../shared/directives/money-input.directive';
import { parseMoney } from '../../../shared/utils/money-input.util';

@Component({
  selector: 'app-customer-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, MoneyInputDirective],
  templateUrl: './customer-form.html',
  styleUrl: './customer-form.scss'
})
export class CustomerForm implements OnInit {
  form: FormGroup;
  isEditMode = signal(false);
  loading = signal(false);
  errorMessage = signal('');
  customerId: string | null = null;

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      documentNumber: ['', Validators.required],
      birthDate: [''],
      phone: [''],
      email: ['', [Validators.required, Validators.email]],
      address: [''],
      createLoginUser: [true],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      createInitialAccount: [true],
      initialAccountType: ['SAVINGS', Validators.required],
      initialBalance: [0, [Validators.required, Validators.min(0)]]
    });

    this.form.get('createLoginUser')?.valueChanges.subscribe(() => this.syncOptionalValidators());
    this.form.get('createInitialAccount')?.valueChanges.subscribe(() => this.syncOptionalValidators());
  }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.paramMap.get('id');
    if (this.customerId) {
      this.isEditMode.set(true);
      this.form.patchValue({
        createLoginUser: false,
        createInitialAccount: false
      });
      this.syncOptionalValidators();
      this.loading.set(true);
      this.customerService.getById(this.customerId).subscribe({
        next: (res) => {
          this.form.patchValue({
            ...res.data,
            birthDate: res.data.birthDate?.substring(0, 10) ?? ''
          });
          this.loading.set(false);
        },
        error: () => {
          this.errorMessage.set('No se pudo cargar el cliente.');
          this.loading.set(false);
        }
      });
    }
  }

  submit(): void {
    this.syncOptionalValidators();
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.errorMessage.set('');

    const payload = this.buildPayload();
    const request$ = this.isEditMode()
      ? this.customerService.update(this.customerId!, payload)
      : this.customerService.create(payload);

    request$.subscribe({
      next: () => this.router.navigate(['/customers']),
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Ocurrio un error al guardar el cliente.');
      }
    });
  }

  private buildPayload(): CustomerRequest {
    const raw = this.form.getRawValue();
    const createLoginUser = !this.isEditMode() && raw.createLoginUser === true;
    const createInitialAccount = !this.isEditMode() && raw.createInitialAccount === true;

    return {
      firstName: raw.firstName.trim(),
      lastName: raw.lastName.trim(),
      documentNumber: raw.documentNumber.trim(),
      birthDate: raw.birthDate || null,
      phone: this.emptyToNull(raw.phone),
      email: raw.email.trim(),
      address: this.emptyToNull(raw.address),
      createLoginUser,
      username: createLoginUser ? raw.username.trim() : null,
      password: createLoginUser ? raw.password : null,
      createInitialAccount,
      initialAccountType: createInitialAccount ? raw.initialAccountType : null,
      initialBalance: createInitialAccount ? parseMoney(raw.initialBalance) : null
    };
  }

  private syncOptionalValidators(): void {
    const loginEnabled = !this.isEditMode() && this.form.get('createLoginUser')?.value === true;
    const accountEnabled = !this.isEditMode() && this.form.get('createInitialAccount')?.value === true;

    this.setValidators('username', loginEnabled ? [Validators.required] : []);
    this.setValidators('password', loginEnabled ? [Validators.required, Validators.minLength(6)] : []);
    this.setValidators('initialAccountType', accountEnabled ? [Validators.required] : []);
    this.setValidators('initialBalance', accountEnabled ? [Validators.required, Validators.min(0)] : []);
  }

  private setValidators(controlName: string, validators: ValidatorFn[]): void {
    const control = this.form.get(controlName);
    control?.setValidators(validators);
    control?.updateValueAndValidity({ emitEvent: false });
  }

  private emptyToNull(value: string): string | null {
    const trimmed = value?.trim();
    return trimmed ? trimmed : null;
  }
}
