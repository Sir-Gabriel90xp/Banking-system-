import { Component, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CurrencyService } from '../../../core/services/currency.service';
import {
  CurrencyInfo,
  ExchangeConversion,
  ExchangeProvider,
  ExchangeRate
} from '../../../shared/models/exchange-rate.model';

@Component({
  selector: 'app-currency-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './currency-dashboard.html',
  styleUrl: './currency-dashboard.scss'
})
export class CurrencyDashboard implements OnInit {
  private readonly fallbackCurrencies: CurrencyInfo[] = [
    { isoCode: 'USD', isoNumeric: '840', name: 'US Dollar', symbol: '$', startDate: '' },
    { isoCode: 'DOP', isoNumeric: '214', name: 'Dominican Peso', symbol: 'RD$', startDate: '' },
    { isoCode: 'EUR', isoNumeric: '978', name: 'Euro', symbol: 'EUR', startDate: '' },
    { isoCode: 'GBP', isoNumeric: '826', name: 'Pound Sterling', symbol: 'GBP', startDate: '' },
    { isoCode: 'CAD', isoNumeric: '124', name: 'Canadian Dollar', symbol: 'CAD', startDate: '' },
    { isoCode: 'MXN', isoNumeric: '484', name: 'Mexican Peso', symbol: 'MXN', startDate: '' }
  ];

  currencies = signal<CurrencyInfo[]>([]);
  providers = signal<ExchangeProvider[]>([]);
  rates = signal<ExchangeRate[]>([]);
  conversion = signal<ExchangeConversion | null>(null);

  loadingCurrencies = signal(false);
  loadingRates = signal(false);
  converting = signal(false);

  ratesError = signal('');
  conversionError = signal('');

  amount = 100;
  base = 'USD';
  quote = 'DOP';

  ratesBase = 'USD';
  ratesQuotes = 'DOP,EUR,GBP,CAD,MXN';
  rateDate = '';
  fromDate = '';
  toDate = '';
  group: 'week' | 'month' | '' = '';
  provider = '';
  includeProviders = false;

  currencyOptions = computed(() => {
    const seen = new Set<string>();
    return [...this.fallbackCurrencies, ...this.currencies()]
      .filter((currency) => {
        if (!currency.isoCode || seen.has(currency.isoCode)) return false;
        seen.add(currency.isoCode);
        return true;
      })
      .sort((left, right) => left.isoCode.localeCompare(right.isoCode));
  });

  constructor(private currencyService: CurrencyService) {}

  ngOnInit(): void {
    this.loadCurrencies();
    this.loadProviders();
    this.convert();
    this.loadRates();
  }

  convert(): void {
    this.conversionError.set('');
    this.converting.set(true);

    this.currencyService.convertDetailed(this.amount, this.base, this.quote).subscribe({
      next: (response) => {
        this.conversion.set(response.data);
        this.converting.set(false);
      },
      error: () => {
        this.conversion.set(null);
        this.conversionError.set('No se pudo calcular la conversion.');
        this.converting.set(false);
      }
    });
  }

  swapCurrencies(): void {
    [this.base, this.quote] = [this.quote, this.base];
    this.convert();
  }

  loadRates(): void {
    this.ratesError.set('');
    this.loadingRates.set(true);

    this.currencyService.getRates({
      base: this.ratesBase,
      quotes: this.ratesQuotes,
      date: this.rateDate,
      from: this.fromDate,
      to: this.toDate,
      group: this.group,
      providers: this.provider,
      includeProviders: this.includeProviders
    }).subscribe({
      next: (response) => {
        this.rates.set(response.data ?? []);
        this.loadingRates.set(false);
      },
      error: () => {
        this.rates.set([]);
        this.ratesError.set('No se pudieron cargar las tasas.');
        this.loadingRates.set(false);
      }
    });
  }

  currencyLabel(currency: CurrencyInfo): string {
    return `${currency.isoCode} - ${currency.name}`;
  }

  private loadCurrencies(): void {
    this.loadingCurrencies.set(true);
    this.currencyService.getCurrencies().subscribe({
      next: (response) => {
        this.currencies.set(response.data ?? []);
        this.loadingCurrencies.set(false);
      },
      error: () => {
        this.currencies.set([]);
        this.loadingCurrencies.set(false);
      }
    });
  }

  private loadProviders(): void {
    this.currencyService.getProviders().subscribe({
      next: (response) => {
        this.providers.set(response.data ?? []);
      },
      error: () => {
        this.providers.set([]);
      }
    });
  }
}
