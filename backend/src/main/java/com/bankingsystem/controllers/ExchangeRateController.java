package com.bankingsystem.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.dto.exchange.CurrencyResponse;
import com.bankingsystem.dto.exchange.ExchangeConversionResponse;
import com.bankingsystem.dto.exchange.ExchangeProviderResponse;
import com.bankingsystem.dto.exchange.ExchangeRateResponse;
import com.bankingsystem.services.ExchangeRateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/rates")
    public ResponseEntity<ApiResponse<List<ExchangeRateResponse>>> getRates(
            @RequestParam(required = false) String base,
            @RequestParam(required = false) String quotes,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String group,
            @RequestParam(required = false) String providers,
            @RequestParam(defaultValue = "false") boolean includeProviders) {

        List<ExchangeRateResponse> rates = exchangeRateService.getRates(
                base,
                quotes,
                date,
                from,
                to,
                group,
                providers,
                includeProviders);

        return ResponseEntity.ok(ApiResponse.success("Exchange rates retrieved successfully.", rates));
    }

    @GetMapping("/rate/{base}/{quote}")
    public ResponseEntity<ApiResponse<ExchangeRateResponse>> getRate(
            @PathVariable String base,
            @PathVariable String quote,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String providers) {

        ExchangeRateResponse rate = exchangeRateService.getRate(base, quote, date, providers);
        return ResponseEntity.ok(ApiResponse.success("Exchange rate retrieved successfully.", rate));
    }

    @GetMapping("/convert")
    public ResponseEntity<ApiResponse<ExchangeConversionResponse>> convert(
            @RequestParam BigDecimal amount,
            @RequestParam String base,
            @RequestParam String quote,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String providers) {

        ExchangeConversionResponse conversion = exchangeRateService.convert(amount, base, quote, date, providers);
        return ResponseEntity.ok(ApiResponse.success("Exchange conversion completed successfully.", conversion));
    }

    @GetMapping("/currencies")
    public ResponseEntity<ApiResponse<List<CurrencyResponse>>> getCurrencies(
            @RequestParam(required = false) String scope) {

        List<CurrencyResponse> currencies = exchangeRateService.getCurrencies(scope);
        return ResponseEntity.ok(ApiResponse.success("Currencies retrieved successfully.", currencies));
    }

    @GetMapping("/currencies/{code}")
    public ResponseEntity<ApiResponse<CurrencyResponse>> getCurrency(@PathVariable String code) {
        CurrencyResponse currency = exchangeRateService.getCurrency(code);
        return ResponseEntity.ok(ApiResponse.success("Currency retrieved successfully.", currency));
    }

    @GetMapping("/providers")
    public ResponseEntity<ApiResponse<List<ExchangeProviderResponse>>> getProviders() {
        List<ExchangeProviderResponse> providers = exchangeRateService.getProviders();
        return ResponseEntity.ok(ApiResponse.success("Exchange rate providers retrieved successfully.", providers));
    }
}
