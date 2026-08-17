package com.bankingsystem.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.dto.market.MarketBasicFinancialsResponse;
import com.bankingsystem.dto.market.MarketCompanyProfileResponse;
import com.bankingsystem.dto.market.MarketHolidayResponse;
import com.bankingsystem.dto.market.MarketNewsResponse;
import com.bankingsystem.dto.market.MarketQuoteResponse;
import com.bankingsystem.dto.market.MarketRecommendationTrendResponse;
import com.bankingsystem.dto.market.MarketStatusResponse;
import com.bankingsystem.dto.market.MarketSymbolSearchResponse;
import com.bankingsystem.services.FinnhubMarketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/markets")
@RequiredArgsConstructor
public class MarketController {

    private final FinnhubMarketService finnhubMarketService;

    @GetMapping("/symbols/search")
    public ResponseEntity<ApiResponse<MarketSymbolSearchResponse>> searchSymbols(
            @RequestParam String q,
            @RequestParam(required = false) String exchange) {

        MarketSymbolSearchResponse response = finnhubMarketService.searchSymbols(q, exchange);
        return ResponseEntity.ok(ApiResponse.success("Market symbols retrieved successfully.", response));
    }

    @GetMapping("/quote")
    public ResponseEntity<ApiResponse<MarketQuoteResponse>> getQuote(@RequestParam String symbol) {
        MarketQuoteResponse response = finnhubMarketService.getQuote(symbol);
        return ResponseEntity.ok(ApiResponse.success("Market quote retrieved successfully.", response));
    }

    @GetMapping("/company-profile")
    public ResponseEntity<ApiResponse<MarketCompanyProfileResponse>> getCompanyProfile(
            @RequestParam String symbol) {

        MarketCompanyProfileResponse response = finnhubMarketService.getCompanyProfile(symbol);
        return ResponseEntity.ok(ApiResponse.success("Company profile retrieved successfully.", response));
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<MarketStatusResponse>> getMarketStatus(
            @RequestParam(defaultValue = "US") String exchange) {

        MarketStatusResponse response = finnhubMarketService.getMarketStatus(exchange);
        return ResponseEntity.ok(ApiResponse.success("Market status retrieved successfully.", response));
    }

    @GetMapping("/holidays")
    public ResponseEntity<ApiResponse<MarketHolidayResponse>> getMarketHolidays(
            @RequestParam(defaultValue = "US") String exchange) {

        MarketHolidayResponse response = finnhubMarketService.getMarketHolidays(exchange);
        return ResponseEntity.ok(ApiResponse.success("Market holidays retrieved successfully.", response));
    }

    @GetMapping("/news")
    public ResponseEntity<ApiResponse<List<MarketNewsResponse>>> getMarketNews(
            @RequestParam(defaultValue = "general") String category,
            @RequestParam(required = false) Long minId) {

        List<MarketNewsResponse> response = finnhubMarketService.getMarketNews(category, minId);
        return ResponseEntity.ok(ApiResponse.success("Market news retrieved successfully.", response));
    }

    @GetMapping("/company-news")
    public ResponseEntity<ApiResponse<List<MarketNewsResponse>>> getCompanyNews(
            @RequestParam String symbol,
            @RequestParam String from,
            @RequestParam String to) {

        List<MarketNewsResponse> response = finnhubMarketService.getCompanyNews(symbol, from, to);
        return ResponseEntity.ok(ApiResponse.success("Company news retrieved successfully.", response));
    }

    @GetMapping("/basic-financials")
    public ResponseEntity<ApiResponse<MarketBasicFinancialsResponse>> getBasicFinancials(
            @RequestParam String symbol) {

        MarketBasicFinancialsResponse response = finnhubMarketService.getBasicFinancials(symbol);
        return ResponseEntity.ok(ApiResponse.success("Basic financials retrieved successfully.", response));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<ApiResponse<List<MarketRecommendationTrendResponse>>> getRecommendations(
            @RequestParam String symbol) {

        List<MarketRecommendationTrendResponse> response = finnhubMarketService.getRecommendations(symbol);
        return ResponseEntity.ok(ApiResponse.success("Recommendation trends retrieved successfully.", response));
    }
}
