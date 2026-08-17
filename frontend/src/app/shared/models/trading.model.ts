export type TradingOrderSide = 'BUY' | 'SELL';
export type TradingOrderStatus = 'FILLED';

export interface TradingPosition {
  id: string;
  symbol: string;
  displaySymbol: string;
  quantity: number;
  averagePrice: number;
  lastPrice: number;
  marketValue: number;
  unrealizedPnl: number;
}

export interface TradingWallet {
  id: string;
  customerId: string;
  cashBalance: number;
  currency: string;
  portfolioValue: number;
  totalEquity: number;
  positions: TradingPosition[];
}

export interface TradingOrder {
  id: string;
  symbol: string;
  displaySymbol: string;
  side: TradingOrderSide;
  quantity: number;
  price: number;
  grossAmount: number;
  status: TradingOrderStatus;
  createdAt: string;
}

export interface TradingOrderPage {
  content: TradingOrder[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

export interface TradingCashRequest {
  accountId: string;
  amount: number;
}

export interface TradingOrderRequest {
  symbol: string;
  quantity: number;
}
