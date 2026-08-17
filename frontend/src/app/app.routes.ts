// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login').then((m) => m.Login)
  },
  {
    path: 'register',
    loadComponent: () => import('./features/auth/register/register').then((m) => m.Register)
  },
  {
    path: '',
    canActivate: [authGuard],
    loadComponent: () => import('./core/layout/shell/shell').then((m) => m.Shell),
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard').then((m) => m.Dashboard)
      },
      {
        path: 'customers',
        canActivate: [roleGuard(['ROLE_ADMIN', 'ROLE_EMPLOYEE'])],
        children: [
          {
            path: '',
            loadComponent: () => import('./features/customers/customer-list/customer-list').then((m) => m.CustomerList)
          },
          {
            path: 'new',
            loadComponent: () => import('./features/customers/customer-form/customer-form').then((m) => m.CustomerForm)
          },
          {
            path: ':id/edit',
            loadComponent: () => import('./features/customers/customer-form/customer-form').then((m) => m.CustomerForm)
          }
        ]
      },
      {
        path: 'accounts',
        children: [
          {
            path: '',
            loadComponent: () => import('./features/accounts/account-list/account-list').then((m) => m.AccountList)
          },
          {
            path: 'new',
            canActivate: [roleGuard(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_CUSTOMER'])],
            loadComponent: () => import('./features/accounts/account-form/account-form').then((m) => m.AccountForm)
          }
        ]
      },
      {
        path: 'transfers',
        children: [
          {
            path: '',
            loadComponent: () => import('./features/transfers/transfer-list/transfer-list').then((m) => m.TransferList)
          },
          {
            path: 'new',
            loadComponent: () => import('./features/transfers/transfer-form/transfer-form').then((m) => m.TransferForm)
          }
        ]
      },
      {
        path: 'loans',
        children: [
          {
            path: '',
            loadComponent: () => import('./features/loans/loan-list/loan-list').then((m) => m.LoanList)
          },
          {
            path: 'new',
            canActivate: [roleGuard(['ROLE_CUSTOMER'])],
            loadComponent: () => import('./features/loans/loan-form/loan-form').then((m) => m.LoanForm)
          }
        ]
      },
      {
        path: 'payments',
        children: [
          {
            path: '',
            loadComponent: () => import('./features/payments/payment-list/payment-list').then((m) => m.PaymentList)
          },
          {
            path: 'new',
            loadComponent: () => import('./features/payments/payments-form/payment-form').then((m) => m.PaymentForm)
          }
        ]
      },
      {
        path: 'exchange-rates',
        loadComponent: () => import('./features/currencies/currency-dashboard/currency-dashboard').then((m) => m.CurrencyDashboard)
      },
      {
        path: 'bpd',
        loadComponent: () => import('./features/bpd/bpd-services/bpd-services').then((m) => m.BpdServices)
      },
      {
        path: 'markets',
        loadComponent: () => import('./features/markets/market-dashboard/market-dashboard').then((m) => m.MarketDashboard)
      },
      {
        path: 'trading',
        loadComponent: () => import('./features/trading/trading-dashboard/trading-dashboard').then((m) => m.TradingDashboard)
      },
      {
        path: 'audit',
        canActivate: [roleGuard(['ROLE_ADMIN'])],
        loadComponent: () => import('./features/audit/audit-list/audit-list').then((m) => m.AuditList)
      },
      {
        path: 'fraud',
        canActivate: [roleGuard(['ROLE_ADMIN', 'ROLE_EMPLOYEE'])],
        loadComponent: () => import('./features/fraud/fraud-list/fraud-list').then((m) => m.FraudList)
      }
    ]
  },
  { path: '**', redirectTo: 'dashboard' }
];
