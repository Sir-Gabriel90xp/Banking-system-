import {
  AfterViewInit,
  Component,
  ElementRef,
  OnDestroy,
  Renderer2,
  ViewChild,
  computed,
  inject,
  signal
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { AccountService } from '../../../core/services/account.service';
import { TradingService } from '../../../core/services/trading.service';
import { Account } from '../../../shared/models/account.model';
import { TradingOrder, TradingWallet } from '../../../shared/models/trading.model';

@Component({
  selector: 'app-trading-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './trading-dashboard.html',
  styleUrl: './trading-dashboard.scss'
})
export class TradingDashboard implements AfterViewInit, OnDestroy {
  @ViewChild('tradingViewHost') private tradingViewHost?: ElementRef<HTMLDivElement>;

  private fb = inject(FormBuilder);

  accounts = signal<Account[]>([]);
  wallet = signal<TradingWallet | null>(null);
  orders = signal<TradingOrder[]>([]);
  loading = signal(false);
  actionLoading = signal(false);
  errorMessage = signal('');
  successMessage = signal('');
  chartSymbol = signal('NASDAQ:AAPL');

  totalBankBalance = computed(() =>
    this.accounts().reduce((sum, account) => sum + Number(account.balance ?? 0), 0)
  );
  positions = computed(() => this.wallet()?.positions ?? []);

  cashForm = this.fb.nonNullable.group({
    accountId: ['', Validators.required],
    amount: [100, [Validators.required, Validators.min(0.01)]]
  });

  orderForm = this.fb.nonNullable.group({
    symbol: ['NASDAQ:AAPL', [Validators.required, Validators.maxLength(40)]],
    quantity: [1, [Validators.required, Validators.min(0.000001)]]
  });

  private viewReady = false;

  constructor(
    private accountService: AccountService,
    private tradingService: TradingService,
    private renderer: Renderer2
  ) {
    this.loadTrading();
  }

  ngAfterViewInit(): void {
    this.viewReady = true;
    this.renderTradingView();
  }

  ngOnDestroy(): void {
    this.clearTradingView();
  }

  loadTrading(): void {
    this.errorMessage.set('');
    this.loading.set(true);

    forkJoin({
      accounts: this.accountService.getMyAccounts(),
      wallet: this.tradingService.portfolio()
    }).subscribe({
      next: ({ accounts, wallet }) => {
        const activeAccounts = (accounts.data ?? []).filter((account) => account.status === 'ACTIVE');
        this.accounts.set(activeAccounts);
        this.wallet.set(wallet.data);
        this.selectDefaultAccount(activeAccounts);
        this.loadOrders();
        this.loading.set(false);
      },
      error: (error) => {
        this.errorMessage.set(error?.error?.message ?? 'No se pudo cargar Trading.');
        this.loading.set(false);
      }
    });
  }

  deposit(): void {
    if (this.cashForm.invalid) {
      this.cashForm.markAllAsTouched();
      return;
    }

    this.runCashAction('deposit');
  }

  withdraw(): void {
    if (this.cashForm.invalid) {
      this.cashForm.markAllAsTouched();
      return;
    }

    this.runCashAction('withdraw');
  }

  buy(): void {
    this.runOrderAction('buy');
  }

  sell(): void {
    this.runOrderAction('sell');
  }

  useSymbol(symbol: string): void {
    const displaySymbol = symbol.includes(':') ? symbol : `NASDAQ:${symbol}`;
    this.orderForm.patchValue({ symbol: displaySymbol });
    this.chartSymbol.set(displaySymbol);
    this.renderTradingView();
  }

  formatMoney(value: number | null | undefined, currency = 'USD'): string {
    return new Intl.NumberFormat('es-DO', {
      style: 'currency',
      currency,
      maximumFractionDigits: 2
    }).format(Number(value ?? 0));
  }

  formatQuantity(value: number | null | undefined): string {
    return new Intl.NumberFormat('es-DO', {
      minimumFractionDigits: 0,
      maximumFractionDigits: 6
    }).format(Number(value ?? 0));
  }

  private runCashAction(action: 'deposit' | 'withdraw'): void {
    const request = this.cashForm.getRawValue();
    this.actionLoading.set(true);
    this.errorMessage.set('');
    this.successMessage.set('');

    this.tradingService[action]({
      accountId: request.accountId,
      amount: Number(request.amount)
    }).subscribe({
      next: (response) => {
        this.wallet.set(response.data);
        this.successMessage.set(action === 'deposit' ? 'Dinero ingresado a Trading.' : 'Dinero retirado a la cuenta.');
        this.actionLoading.set(false);
        this.refreshAccountsAndOrders();
      },
      error: (error) => {
        this.errorMessage.set(error?.error?.message ?? 'No se pudo completar la operacion.');
        this.actionLoading.set(false);
      }
    });
  }

  private runOrderAction(action: 'buy' | 'sell'): void {
    if (this.orderForm.invalid) {
      this.orderForm.markAllAsTouched();
      return;
    }

    const request = this.orderForm.getRawValue();
    const displaySymbol = this.normalizeDisplaySymbol(request.symbol);
    this.chartSymbol.set(displaySymbol);
    this.renderTradingView();
    this.actionLoading.set(true);
    this.errorMessage.set('');
    this.successMessage.set('');

    this.tradingService[action]({
      symbol: displaySymbol,
      quantity: Number(request.quantity)
    }).subscribe({
      next: (response) => {
        this.wallet.set(response.data);
        this.successMessage.set(action === 'buy' ? 'Compra ejecutada.' : 'Venta ejecutada.');
        this.actionLoading.set(false);
        this.refreshAccountsAndOrders();
      },
      error: (error) => {
        this.errorMessage.set(error?.error?.message ?? 'No se pudo ejecutar la orden.');
        this.actionLoading.set(false);
      }
    });
  }

  private refreshAccountsAndOrders(): void {
    forkJoin({
      accounts: this.accountService.getMyAccounts(),
      orders: this.tradingService.orders()
    }).subscribe({
      next: ({ accounts, orders }) => {
        this.accounts.set((accounts.data ?? []).filter((account) => account.status === 'ACTIVE'));
        this.orders.set(orders.data?.content ?? []);
      }
    });
  }

  private loadOrders(): void {
    this.tradingService.orders().subscribe({
      next: (response) => this.orders.set(response.data?.content ?? []),
      error: () => this.orders.set([])
    });
  }

  private selectDefaultAccount(accounts: Account[]): void {
    const current = this.cashForm.controls.accountId.value;
    if (!current && accounts.length > 0) {
      this.cashForm.patchValue({ accountId: accounts[0].id });
    }
  }

  private normalizeDisplaySymbol(rawSymbol: string): string {
    const compact = rawSymbol.trim().replace(/\s+/g, '').toUpperCase();
    return compact.includes(':') ? compact : `NASDAQ:${compact}`;
  }

  private renderTradingView(): void {
    if (!this.viewReady || !this.tradingViewHost) {
      return;
    }

    this.clearTradingView();

    const container = this.renderer.createElement('div');
    this.renderer.addClass(container, 'tradingview-widget-container');
    this.renderer.setStyle(container, 'height', '100%');
    this.renderer.setStyle(container, 'width', '100%');

    const widget = this.renderer.createElement('div');
    this.renderer.addClass(widget, 'tradingview-widget-container__widget');
    this.renderer.setStyle(widget, 'height', 'calc(100% - 32px)');
    this.renderer.setStyle(widget, 'width', '100%');

    const copyright = this.renderer.createElement('div');
    this.renderer.addClass(copyright, 'tradingview-widget-copyright');
    const link = this.renderer.createElement('a');
    this.renderer.setAttribute(link, 'href', 'https://www.tradingview.com/');
    this.renderer.setAttribute(link, 'rel', 'noopener nofollow');
    this.renderer.setAttribute(link, 'target', '_blank');
    const label = this.renderer.createText(`${this.chartSymbol()} chart by TradingView`);
    this.renderer.appendChild(link, label);
    this.renderer.appendChild(copyright, link);

    const script = this.renderer.createElement('script');
    this.renderer.setAttribute(script, 'type', 'text/javascript');
    this.renderer.setAttribute(script, 'src', 'https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js');
    this.renderer.setAttribute(script, 'async', 'true');
    script.text = JSON.stringify({
      allow_symbol_change: true,
      calendar: false,
      details: false,
      hide_side_toolbar: true,
      hide_top_toolbar: false,
      hide_legend: false,
      hide_volume: false,
      hotlist: false,
      interval: 'D',
      locale: 'en',
      save_image: true,
      style: '1',
      symbol: this.chartSymbol(),
      theme: 'dark',
      timezone: 'Etc/UTC',
      backgroundColor: '#0F0F0F',
      gridColor: 'rgba(242, 242, 242, 0.2)',
      watchlist: [],
      withdateranges: false,
      compareSymbols: [],
      support_host: 'https://www.tradingview.com',
      studies: [],
      autosize: true
    });

    this.renderer.appendChild(container, widget);
    this.renderer.appendChild(container, copyright);
    this.renderer.appendChild(container, script);
    this.renderer.appendChild(this.tradingViewHost.nativeElement, container);
  }

  private clearTradingView(): void {
    const host = this.tradingViewHost?.nativeElement;
    if (!host) {
      return;
    }

    while (host.firstChild) {
      this.renderer.removeChild(host, host.firstChild);
    }
  }
}
