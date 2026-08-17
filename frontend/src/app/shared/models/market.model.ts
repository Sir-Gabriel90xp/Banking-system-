export interface MarketSymbolSearchItem {
  description: string;
  displaySymbol: string;
  symbol: string;
  type: string;
}

export interface MarketSymbolSearch {
  count: number;
  result: MarketSymbolSearchItem[];
}

export interface MarketQuote {
  symbol: string;
  currentPrice: number | null;
  change: number | null;
  percentChange: number | null;
  highPriceOfDay: number | null;
  lowPriceOfDay: number | null;
  openPriceOfDay: number | null;
  previousClosePrice: number | null;
  timestamp: number | null;
}

export interface MarketCompanyProfile {
  country: string;
  currency: string;
  exchange: string;
  ipo: string;
  marketCapitalization: number | null;
  name: string;
  phone: string;
  shareOutstanding: number | null;
  ticker: string;
  weburl: string;
  logo: string;
  finnhubIndustry: string;
}

export interface MarketStatus {
  exchange: string;
  holiday: string | null;
  isOpen: boolean | null;
  session: string | null;
  timezone: string;
  timestamp: number | null;
}

export interface MarketHolidayItem {
  eventName: string;
  atDate: string;
  tradingHour: string;
}

export interface MarketHoliday {
  exchange: string;
  timezone: string;
  data: MarketHolidayItem[];
}

export interface MarketNews {
  category: string;
  datetime: number | null;
  headline: string;
  id: number | null;
  image: string;
  related: string;
  source: string;
  summary: string;
  url: string;
}

export interface MarketBasicFinancials {
  symbol: string;
  metric: Record<string, number | string | null>;
}

export interface MarketRecommendationTrend {
  buy: number | null;
  hold: number | null;
  period: string;
  sell: number | null;
  strongBuy: number | null;
  strongSell: number | null;
  symbol: string;
}
