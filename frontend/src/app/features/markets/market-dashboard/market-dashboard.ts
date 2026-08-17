import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { MarketService } from '../../../core/services/market.service';
import {
  MarketBasicFinancials,
  MarketCompanyProfile,
  MarketHoliday,
  MarketNews,
  MarketQuote,
  MarketRecommendationTrend,
  MarketStatus,
  MarketSymbolSearchItem
} from '../../../shared/models/market.model';

@Component({
  selector: 'app-market-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './market-dashboard.html',
  styleUrl: './market-dashboard.scss'
})
export class MarketDashboard implements OnInit {
  query = 'apple';
  exchange = 'US';
  selectedSymbol = 'AAPL';
  newsCategory: 'general' | 'forex' | 'crypto' | 'merger' = 'general';

  searchResults = signal<MarketSymbolSearchItem[]>([]);
  quote = signal<MarketQuote | null>(null);
  profile = signal<MarketCompanyProfile | null>(null);
  status = signal<MarketStatus | null>(null);
  holidays = signal<MarketHoliday | null>(null);
  marketNews = signal<MarketNews[]>([]);
  companyNews = signal<MarketNews[]>([]);
  financials = signal<MarketBasicFinancials | null>(null);
  recommendations = signal<MarketRecommendationTrend[]>([]);

  loadingSearch = signal(false);
  loadingSymbol = signal(false);
  loadingMarket = signal(false);
  errorMessage = signal('');

  constructor(private marketService: MarketService) {}

  ngOnInit(): void {
    this.searchSymbols();
    this.loadSymbol();
    this.loadMarket();
  }

  searchSymbols(): void {
    this.errorMessage.set('');
    this.loadingSearch.set(true);
    this.marketService.searchSymbols(this.query, this.exchange).subscribe({
      next: (response) => {
        this.searchResults.set(response.data?.result ?? []);
        this.loadingSearch.set(false);
      },
      error: (error) => {
        this.errorMessage.set(error?.error?.message ?? 'No se pudo buscar simbolos.');
        this.searchResults.set([]);
        this.loadingSearch.set(false);
      }
    });
  }

  selectSymbol(symbol: string): void {
    this.selectedSymbol = symbol;
    this.loadSymbol();
  }

  loadSymbol(): void {
    this.errorMessage.set('');
    this.loadingSymbol.set(true);
    const range = this.companyNewsRange();

    forkJoin({
      quote: this.marketService.getQuote(this.selectedSymbol),
      profile: this.marketService.getCompanyProfile(this.selectedSymbol),
      news: this.marketService.getCompanyNews(this.selectedSymbol, range.from, range.to),
      financials: this.marketService.getBasicFinancials(this.selectedSymbol),
      recommendations: this.marketService.getRecommendations(this.selectedSymbol)
    }).subscribe({
      next: (response) => {
        this.quote.set(response.quote.data);
        this.profile.set(response.profile.data);
        this.companyNews.set((response.news.data ?? []).slice(0, 5));
        this.financials.set(response.financials.data);
        this.recommendations.set(response.recommendations.data ?? []);
        this.loadingSymbol.set(false);
      },
      error: (error) => {
        this.errorMessage.set(error?.error?.message ?? 'No se pudo cargar el simbolo.');
        this.loadingSymbol.set(false);
      }
    });
  }

  loadMarket(): void {
    this.errorMessage.set('');
    this.loadingMarket.set(true);

    forkJoin({
      status: this.marketService.getMarketStatus(this.exchange),
      holidays: this.marketService.getMarketHolidays(this.exchange),
      news: this.marketService.getMarketNews(this.newsCategory)
    }).subscribe({
      next: (response) => {
        this.status.set(response.status.data);
        this.holidays.set(response.holidays.data);
        this.marketNews.set((response.news.data ?? []).slice(0, 6));
        this.loadingMarket.set(false);
      },
      error: (error) => {
        this.errorMessage.set(error?.error?.message ?? 'No se pudo cargar el mercado.');
        this.loadingMarket.set(false);
      }
    });
  }

  latestRecommendation(): MarketRecommendationTrend | null {
    return this.recommendations()[0] ?? null;
  }

  metricValue(key: string): number | string | null {
    return this.financials()?.metric?.[key] ?? null;
  }

  formatUnix(timestamp: number | null): string {
    if (!timestamp) return '-';
    return new Date(timestamp * 1000).toLocaleDateString('es-DO', {
      day: '2-digit',
      month: 'short',
      year: 'numeric'
    });
  }

  private companyNewsRange(): { from: string; to: string } {
    const to = new Date();
    const from = new Date(to);
    from.setDate(to.getDate() - 14);
    return {
      from: from.toISOString().slice(0, 10),
      to: to.toISOString().slice(0, 10)
    };
  }
}
