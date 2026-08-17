export interface ExchangeRateProviderContribution {
  key: string;
  date: string;
  rate: number;
  excluded?: boolean | null;
}

export interface ExchangeRate {
  date: string;
  base: string;
  quote: string;
  rate: number;
  providers: ExchangeRateProviderContribution[];
}

export interface CurrencyInfo {
  isoCode: string;
  isoNumeric: string;
  name: string;
  symbol: string;
  startDate: string;
}

export interface ExchangeProvider {
  key: string;
  name: string;
  countryCode: string;
  rateType: string;
  pivotCurrency: string;
}

export interface ExchangeConversion {
  date: string;
  base: string;
  quote: string;
  amount: number;
  rate: number;
  convertedAmount: number;
}

export interface ExchangeRatesQuery {
  base?: string;
  quotes?: string;
  date?: string;
  from?: string;
  to?: string;
  group?: 'week' | 'month' | '';
  providers?: string;
  includeProviders?: boolean;
}
