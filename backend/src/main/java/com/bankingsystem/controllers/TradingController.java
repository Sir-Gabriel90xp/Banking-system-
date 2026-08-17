package com.bankingsystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.dto.trading.TradingCashRequest;
import com.bankingsystem.dto.trading.TradingOrderRequest;
import com.bankingsystem.dto.trading.TradingOrderResponse;
import com.bankingsystem.dto.trading.TradingWalletResponse;
import com.bankingsystem.services.TradingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/trading")
@RequiredArgsConstructor
public class TradingController {

    private final TradingService tradingService;

    @GetMapping("/portfolio")
    public ResponseEntity<ApiResponse<TradingWalletResponse>> getPortfolio(Authentication authentication) {
        TradingWalletResponse response = tradingService.getPortfolio(authentication);
        return ResponseEntity.ok(ApiResponse.success("Trading portfolio retrieved successfully.", response));
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<TradingWalletResponse>> deposit(
            @Valid @RequestBody TradingCashRequest request,
            Authentication authentication) {

        TradingWalletResponse response = tradingService.deposit(request, authentication);
        return ResponseEntity.ok(ApiResponse.success("Trading cash deposited successfully.", response));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<TradingWalletResponse>> withdraw(
            @Valid @RequestBody TradingCashRequest request,
            Authentication authentication) {

        TradingWalletResponse response = tradingService.withdraw(request, authentication);
        return ResponseEntity.ok(ApiResponse.success("Trading cash withdrawn successfully.", response));
    }

    @PostMapping("/buy")
    public ResponseEntity<ApiResponse<TradingWalletResponse>> buy(
            @Valid @RequestBody TradingOrderRequest request,
            Authentication authentication) {

        TradingWalletResponse response = tradingService.buy(request, authentication);
        return ResponseEntity.ok(ApiResponse.success("Trading buy order filled successfully.", response));
    }

    @PostMapping("/sell")
    public ResponseEntity<ApiResponse<TradingWalletResponse>> sell(
            @Valid @RequestBody TradingOrderRequest request,
            Authentication authentication) {

        TradingWalletResponse response = tradingService.sell(request, authentication);
        return ResponseEntity.ok(ApiResponse.success("Trading sell order filled successfully.", response));
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<Page<TradingOrderResponse>>> getOrders(
            @PageableDefault(size = 10) Pageable pageable,
            Authentication authentication) {

        Page<TradingOrderResponse> response = tradingService.getOrders(authentication, pageable);
        return ResponseEntity.ok(ApiResponse.success("Trading orders retrieved successfully.", response));
    }
}
