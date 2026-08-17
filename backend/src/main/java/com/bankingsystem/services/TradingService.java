package com.bankingsystem.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bankingsystem.common.AuditContext;
import com.bankingsystem.dto.market.MarketQuoteResponse;
import com.bankingsystem.dto.trading.TradingCashRequest;
import com.bankingsystem.dto.trading.TradingOrderRequest;
import com.bankingsystem.dto.trading.TradingOrderResponse;
import com.bankingsystem.dto.trading.TradingPositionResponse;
import com.bankingsystem.dto.trading.TradingWalletResponse;
import com.bankingsystem.entities.Account;
import com.bankingsystem.entities.Customer;
import com.bankingsystem.entities.TradingOrder;
import com.bankingsystem.entities.TradingPosition;
import com.bankingsystem.entities.TradingWallet;
import com.bankingsystem.entities.enums.AccountStatus;
import com.bankingsystem.entities.enums.TradingOrderSide;
import com.bankingsystem.entities.enums.TradingOrderStatus;
import com.bankingsystem.events.audit.AuditEvent;
import com.bankingsystem.events.audit.AuditEventPublisher;
import com.bankingsystem.repositories.AccountRepository;
import com.bankingsystem.repositories.CustomerRepository;
import com.bankingsystem.repositories.TradingOrderRepository;
import com.bankingsystem.repositories.TradingPositionRepository;
import com.bankingsystem.repositories.TradingWalletRepository;
import com.bankingsystem.security.AppUserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradingService {

    private static final int MONEY_SCALE = 2;
    private static final int PRICE_SCALE = 4;
    private static final int QUANTITY_SCALE = 6;

    private final TradingWalletRepository tradingWalletRepository;
    private final TradingPositionRepository tradingPositionRepository;
    private final TradingOrderRepository tradingOrderRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final FinnhubMarketService finnhubMarketService;
    private final AuditEventPublisher auditEventPublisher;
    private final AuditContext auditContext;

    @Transactional
    public TradingWalletResponse getPortfolio(Authentication authentication) {
        Customer customer = resolveAuthenticatedCustomer(authentication);
        TradingWallet wallet = getOrCreateWallet(customer);
        return toWalletResponse(wallet);
    }

    @Transactional
    public TradingWalletResponse deposit(TradingCashRequest request, Authentication authentication) {
        BigDecimal amount = money(request.amount());
        assertPositive(amount, "Amount must be greater than zero.");

        Customer customer = resolveAuthenticatedCustomer(authentication);
        Account account = resolveOwnedActiveAccount(request.accountId(), customer);
        TradingWallet wallet = getOrCreateWallet(customer);

        accountService.debit(account.getId(), amount);
        wallet.setCashBalance(money(wallet.getCashBalance().add(amount)));
        TradingWallet saved = tradingWalletRepository.save(wallet);

        publishAudit(authentication, "TRADING_DEPOSIT", "TradingWallet", saved.getId());
        return toWalletResponse(saved);
    }

    @Transactional
    public TradingWalletResponse withdraw(TradingCashRequest request, Authentication authentication) {
        BigDecimal amount = money(request.amount());
        assertPositive(amount, "Amount must be greater than zero.");

        Customer customer = resolveAuthenticatedCustomer(authentication);
        Account account = resolveOwnedActiveAccount(request.accountId(), customer);
        TradingWallet wallet = getOrCreateWallet(customer);

        if (wallet.getCashBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), "Insufficient trading cash balance.");
        }

        wallet.setCashBalance(money(wallet.getCashBalance().subtract(amount)));
        TradingWallet saved = tradingWalletRepository.save(wallet);
        accountService.credit(account.getId(), amount);

        publishAudit(authentication, "TRADING_WITHDRAW", "TradingWallet", saved.getId());
        return toWalletResponse(saved);
    }

    @Transactional
    public TradingWalletResponse buy(TradingOrderRequest request, Authentication authentication) {
        Customer customer = resolveAuthenticatedCustomer(authentication);
        TradingWallet wallet = getOrCreateWallet(customer);
        OrderInput input = resolveOrderInput(request);

        BigDecimal grossAmount = money(input.price().multiply(input.quantity()));
        assertPositive(grossAmount, "Order amount is too small.");

        if (wallet.getCashBalance().compareTo(grossAmount) < 0) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), "Insufficient trading cash balance.");
        }

        TradingPosition position = tradingPositionRepository
                .findByWalletIdAndSymbol(wallet.getId(), input.symbol())
                .orElseGet(() -> newPosition(wallet, input));

        BigDecimal currentQuantity = safe(position.getQuantity());
        BigDecimal currentCost = position.getAveragePrice().multiply(currentQuantity);
        BigDecimal newQuantity = quantity(currentQuantity.add(input.quantity()));
        BigDecimal newCost = currentCost.add(grossAmount);

        position.setQuantity(newQuantity);
        position.setAveragePrice(price(newCost.divide(newQuantity, PRICE_SCALE, RoundingMode.HALF_UP)));
        tradingPositionRepository.save(position);

        wallet.setCashBalance(money(wallet.getCashBalance().subtract(grossAmount)));
        TradingWallet saved = tradingWalletRepository.save(wallet);

        TradingOrder order = newOrder(saved, input, TradingOrderSide.BUY, grossAmount);
        tradingOrderRepository.save(order);

        publishAudit(authentication, "TRADING_BUY", "TradingOrder", order.getId());
        return toWalletResponse(saved);
    }

    @Transactional
    public TradingWalletResponse sell(TradingOrderRequest request, Authentication authentication) {
        Customer customer = resolveAuthenticatedCustomer(authentication);
        TradingWallet wallet = getOrCreateWallet(customer);
        OrderInput input = resolveOrderInput(request);

        TradingPosition position = tradingPositionRepository
                .findByWalletIdAndSymbol(wallet.getId(), input.symbol())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(422), "No position for symbol."));

        if (position.getQuantity().compareTo(input.quantity()) < 0) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), "Insufficient position quantity.");
        }

        BigDecimal grossAmount = money(input.price().multiply(input.quantity()));
        assertPositive(grossAmount, "Order amount is too small.");

        BigDecimal remainingQuantity = quantity(position.getQuantity().subtract(input.quantity()));
        if (remainingQuantity.compareTo(BigDecimal.ZERO) == 0) {
            tradingPositionRepository.delete(position);
        } else {
            position.setQuantity(remainingQuantity);
            tradingPositionRepository.save(position);
        }

        wallet.setCashBalance(money(wallet.getCashBalance().add(grossAmount)));
        TradingWallet saved = tradingWalletRepository.save(wallet);

        TradingOrder order = newOrder(saved, input, TradingOrderSide.SELL, grossAmount);
        tradingOrderRepository.save(order);

        publishAudit(authentication, "TRADING_SELL", "TradingOrder", order.getId());
        return toWalletResponse(saved);
    }

    @Transactional
    public Page<TradingOrderResponse> getOrders(Authentication authentication, Pageable pageable) {
        Customer customer = resolveAuthenticatedCustomer(authentication);
        TradingWallet wallet = getOrCreateWallet(customer);

        return tradingOrderRepository.findByWalletId(wallet.getId(), pageable)
                .map(this::toOrderResponse);
    }

    private TradingWallet getOrCreateWallet(Customer customer) {
        return tradingWalletRepository.findByCustomerId(customer.getId())
                .orElseGet(() -> {
                    TradingWallet wallet = new TradingWallet();
                    wallet.setCustomer(customer);
                    wallet.setCashBalance(BigDecimal.ZERO.setScale(MONEY_SCALE, RoundingMode.HALF_UP));
                    wallet.setCurrency("USD");
                    return tradingWalletRepository.save(wallet);
                });
    }

    private Customer resolveAuthenticatedCustomer(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AppUserPrincipal principal)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required.");
        }

        return customerRepository.findByUser_Id(principal.getId())
                .filter(customer -> !customer.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "The authenticated user is not linked to a customer."));
    }

    private Account resolveOwnedActiveAccount(UUID accountId, Customer customer) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found."));

        if (account.isDeleted() || !account.getCustomer().getId().equals(customer.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this account.");
        }
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), "Account is not active.");
        }

        return account;
    }

    private OrderInput resolveOrderInput(TradingOrderRequest request) {
        String displaySymbol = normalizeDisplaySymbol(request.symbol());
        String symbol = normalizeExecutionSymbol(displaySymbol);
        BigDecimal executionQuantity = quantity(request.quantity());
        assertPositive(executionQuantity, "Quantity must be greater than zero.");

        BigDecimal executionPrice = resolveExecutionPrice(symbol);
        return new OrderInput(symbol, displaySymbol, executionQuantity, executionPrice);
    }

    private BigDecimal resolveExecutionPrice(String symbol) {
        try {
            MarketQuoteResponse quote = finnhubMarketService.getQuote(symbol);
            if (quote.currentPrice() != null && quote.currentPrice().compareTo(BigDecimal.ZERO) > 0) {
                return price(quote.currentPrice());
            }
        } catch (RuntimeException ignored) {
            // Trading keeps working in local/demo mode when FINNHUB_API_KEY is not configured.
        }

        return demoPrice(symbol);
    }

    private TradingPosition newPosition(TradingWallet wallet, OrderInput input) {
        TradingPosition position = new TradingPosition();
        position.setWallet(wallet);
        position.setSymbol(input.symbol());
        position.setDisplaySymbol(input.displaySymbol());
        position.setQuantity(BigDecimal.ZERO.setScale(QUANTITY_SCALE, RoundingMode.HALF_UP));
        position.setAveragePrice(BigDecimal.ZERO.setScale(PRICE_SCALE, RoundingMode.HALF_UP));
        return position;
    }

    private TradingOrder newOrder(TradingWallet wallet, OrderInput input, TradingOrderSide side,
            BigDecimal grossAmount) {
        TradingOrder order = new TradingOrder();
        order.setWallet(wallet);
        order.setSymbol(input.symbol());
        order.setDisplaySymbol(input.displaySymbol());
        order.setSide(side);
        order.setQuantity(input.quantity());
        order.setPrice(input.price());
        order.setGrossAmount(grossAmount);
        order.setStatus(TradingOrderStatus.FILLED);
        return order;
    }

    private TradingWalletResponse toWalletResponse(TradingWallet wallet) {
        List<TradingPositionResponse> positions = tradingPositionRepository
                .findByWalletIdOrderBySymbolAsc(wallet.getId())
                .stream()
                .map(this::toPositionResponse)
                .toList();

        BigDecimal portfolioValue = positions.stream()
                .map(TradingPositionResponse::marketValue)
                .reduce(BigDecimal.ZERO.setScale(MONEY_SCALE, RoundingMode.HALF_UP), BigDecimal::add);
        portfolioValue = money(portfolioValue);

        BigDecimal cashBalance = money(wallet.getCashBalance());
        return new TradingWalletResponse(
                wallet.getId(),
                wallet.getCustomer().getId(),
                cashBalance,
                wallet.getCurrency(),
                portfolioValue,
                money(cashBalance.add(portfolioValue)),
                positions);
    }

    private TradingPositionResponse toPositionResponse(TradingPosition position) {
        BigDecimal lastPrice = resolveExecutionPrice(position.getSymbol());
        BigDecimal marketValue = money(lastPrice.multiply(position.getQuantity()));
        BigDecimal costBasis = money(position.getAveragePrice().multiply(position.getQuantity()));

        return new TradingPositionResponse(
                position.getId(),
                position.getSymbol(),
                position.getDisplaySymbol(),
                quantity(position.getQuantity()),
                price(position.getAveragePrice()),
                lastPrice,
                marketValue,
                money(marketValue.subtract(costBasis)));
    }

    private TradingOrderResponse toOrderResponse(TradingOrder order) {
        return new TradingOrderResponse(
                order.getId(),
                order.getSymbol(),
                order.getDisplaySymbol(),
                order.getSide(),
                quantity(order.getQuantity()),
                price(order.getPrice()),
                money(order.getGrossAmount()),
                order.getStatus(),
                order.getCreatedAt());
    }

    private void publishAudit(Authentication authentication, String action, String entity, UUID entityId) {
        auditEventPublisher.publish(AuditEvent.of(
                auditContext.resolveUserId(authentication),
                action,
                entity,
                entityId,
                auditContext.resolveClientIp()));
    }

    private String normalizeDisplaySymbol(String rawSymbol) {
        if (rawSymbol == null || rawSymbol.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Symbol is required.");
        }

        String normalized = rawSymbol.trim().replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
        if (normalized.length() > 40) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Symbol cannot exceed 40 characters.");
        }
        return normalized;
    }

    private String normalizeExecutionSymbol(String displaySymbol) {
        int separatorIndex = displaySymbol.indexOf(':');
        String symbol = separatorIndex >= 0 ? displaySymbol.substring(separatorIndex + 1) : displaySymbol;

        if (symbol.isBlank() || symbol.length() > 30) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid trading symbol.");
        }
        return symbol;
    }

    private BigDecimal demoPrice(String symbol) {
        long bucket = Math.abs((long) symbol.hashCode());
        BigDecimal dollars = BigDecimal.valueOf(50 + (bucket % 450));
        BigDecimal cents = BigDecimal.valueOf(bucket % 100).movePointLeft(2);
        return price(dollars.add(cents));
    }

    private BigDecimal money(BigDecimal value) {
        return safe(value).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal price(BigDecimal value) {
        return safe(value).setScale(PRICE_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal quantity(BigDecimal value) {
        return safe(value).setScale(QUANTITY_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal safe(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private void assertPositive(BigDecimal value, String message) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), message);
        }
    }

    private record OrderInput(
            String symbol,
            String displaySymbol,
            BigDecimal quantity,
            BigDecimal price) {
    }
}
