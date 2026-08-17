package com.bankingsystem.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriBuilder;

import com.bankingsystem.dto.market.MarketBasicFinancialsResponse;
import com.bankingsystem.dto.market.MarketCompanyProfileResponse;
import com.bankingsystem.dto.market.MarketHolidayItemResponse;
import com.bankingsystem.dto.market.MarketHolidayResponse;
import com.bankingsystem.dto.market.MarketNewsResponse;
import com.bankingsystem.dto.market.MarketQuoteResponse;
import com.bankingsystem.dto.market.MarketRecommendationTrendResponse;
import com.bankingsystem.dto.market.MarketStatusResponse;
import com.bankingsystem.dto.market.MarketSymbolSearchItemResponse;
import com.bankingsystem.dto.market.MarketSymbolSearchResponse;

@Service
public class FinnhubMarketService {

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {};

    private static final ParameterizedTypeReference<List<Map<String, Object>>> MAP_LIST_TYPE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;
    private final String apiKey;

    public FinnhubMarketService(
            RestClient.Builder restClientBuilder,
            @Value("${app.finnhub.base-url}") String baseUrl,
            @Value("${app.finnhub.api-key}") String apiKey) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public MarketSymbolSearchResponse searchSymbols(String query, String exchange) {
        ensureConfigured();
        if (query == null || query.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Query is required.");
        }

        Map<String, Object> response = getMap("/search", uriBuilder -> {
            uriBuilder.queryParam("q", query.trim());
            addQueryParam(uriBuilder, "exchange", normalizeOptionalExchange(exchange));
        });

        List<MarketSymbolSearchItemResponse> result = listValue(response.get("result")).stream()
                .map(this::mapSearchItem)
                .toList();

        return new MarketSymbolSearchResponse(intValue(response, "count"), result);
    }

    public MarketQuoteResponse getQuote(String symbol) {
        ensureConfigured();
        String normalizedSymbol = normalizeSymbol(symbol);

        Map<String, Object> response = getMap("/quote", uriBuilder ->
                uriBuilder.queryParam("symbol", normalizedSymbol));

        return new MarketQuoteResponse(
                normalizedSymbol,
                decimalValue(response, "c"),
                decimalValue(response, "d"),
                decimalValue(response, "dp"),
                decimalValue(response, "h"),
                decimalValue(response, "l"),
                decimalValue(response, "o"),
                decimalValue(response, "pc"),
                longValue(response, "t"));
    }

    public MarketCompanyProfileResponse getCompanyProfile(String symbol) {
        ensureConfigured();
        String normalizedSymbol = normalizeSymbol(symbol);

        Map<String, Object> response = getMap("/stock/profile2", uriBuilder ->
                uriBuilder.queryParam("symbol", normalizedSymbol));

        return new MarketCompanyProfileResponse(
                stringValue(response, "country"),
                stringValue(response, "currency"),
                stringValue(response, "exchange"),
                stringValue(response, "ipo"),
                decimalValue(response, "marketCapitalization"),
                stringValue(response, "name"),
                stringValue(response, "phone"),
                decimalValue(response, "shareOutstanding"),
                stringValue(response, "ticker"),
                stringValue(response, "weburl"),
                stringValue(response, "logo"),
                stringValue(response, "finnhubIndustry"));
    }

    public MarketStatusResponse getMarketStatus(String exchange) {
        ensureConfigured();
        String normalizedExchange = normalizeExchange(exchange);

        Map<String, Object> response = getMap("/stock/market-status", uriBuilder ->
                uriBuilder.queryParam("exchange", normalizedExchange));

        return new MarketStatusResponse(
                stringValue(response, "exchange"),
                stringValue(response, "holiday"),
                booleanValue(response, "isOpen"),
                stringValue(response, "session"),
                stringValue(response, "timezone"),
                longValue(response, "t"));
    }

    public MarketHolidayResponse getMarketHolidays(String exchange) {
        ensureConfigured();
        String normalizedExchange = normalizeExchange(exchange);

        Map<String, Object> response = getMap("/stock/market-holiday", uriBuilder ->
                uriBuilder.queryParam("exchange", normalizedExchange));

        List<MarketHolidayItemResponse> holidays = listValue(response.get("data")).stream()
                .map(item -> new MarketHolidayItemResponse(
                        stringValue(item, "eventName"),
                        stringValue(item, "atDate"),
                        stringValue(item, "tradingHour")))
                .toList();

        return new MarketHolidayResponse(
                stringValue(response, "exchange"),
                stringValue(response, "timezone"),
                holidays);
    }

    public List<MarketNewsResponse> getMarketNews(String category, Long minId) {
        ensureConfigured();
        String normalizedCategory = normalizeNewsCategory(category);

        List<Map<String, Object>> response = getMapList("/news", uriBuilder -> {
            uriBuilder.queryParam("category", normalizedCategory);
            if (minId != null && minId > 0) {
                uriBuilder.queryParam("minId", minId);
            }
        });

        return response.stream().map(this::mapNews).toList();
    }

    public List<MarketNewsResponse> getCompanyNews(String symbol, String from, String to) {
        ensureConfigured();
        String normalizedSymbol = normalizeSymbol(symbol);
        String normalizedFrom = normalizeDate(from, "from");
        String normalizedTo = normalizeDate(to, "to");

        List<Map<String, Object>> response = getMapList("/company-news", uriBuilder -> {
            uriBuilder.queryParam("symbol", normalizedSymbol);
            uriBuilder.queryParam("from", normalizedFrom);
            uriBuilder.queryParam("to", normalizedTo);
        });

        return response.stream().map(this::mapNews).toList();
    }

    public MarketBasicFinancialsResponse getBasicFinancials(String symbol) {
        ensureConfigured();
        String normalizedSymbol = normalizeSymbol(symbol);

        Map<String, Object> response = getMap("/stock/metric", uriBuilder -> {
            uriBuilder.queryParam("symbol", normalizedSymbol);
            uriBuilder.queryParam("metric", "all");
        });

        return new MarketBasicFinancialsResponse(
                stringValue(response, "symbol"),
                mapValue(response.get("metric")));
    }

    public List<MarketRecommendationTrendResponse> getRecommendations(String symbol) {
        ensureConfigured();
        String normalizedSymbol = normalizeSymbol(symbol);

        List<Map<String, Object>> response = getMapList("/stock/recommendation", uriBuilder ->
                uriBuilder.queryParam("symbol", normalizedSymbol));

        return response.stream()
                .map(item -> new MarketRecommendationTrendResponse(
                        integerValue(item, "buy"),
                        integerValue(item, "hold"),
                        stringValue(item, "period"),
                        integerValue(item, "sell"),
                        integerValue(item, "strongBuy"),
                        integerValue(item, "strongSell"),
                        stringValue(item, "symbol")))
                .toList();
    }

    private Map<String, Object> getMap(String path, QueryBuilder queryBuilder) {
        Map<String, Object> response = execute(() -> restClient.get()
                .uri(uriBuilder -> {
                    UriBuilder builder = uriBuilder.path(path);
                    queryBuilder.apply(builder);
                    return builder.build();
                })
                .header("X-Finnhub-Token", apiKey)
                .retrieve()
                .body(MAP_TYPE));

        return response != null ? response : Map.of();
    }

    private List<Map<String, Object>> getMapList(String path, QueryBuilder queryBuilder) {
        List<Map<String, Object>> response = execute(() -> restClient.get()
                .uri(uriBuilder -> {
                    UriBuilder builder = uriBuilder.path(path);
                    queryBuilder.apply(builder);
                    return builder.build();
                })
                .header("X-Finnhub-Token", apiKey)
                .retrieve()
                .body(MAP_LIST_TYPE));

        return response != null ? response : List.of();
    }

    private <T> T execute(ProviderRequest<T> request) {
        try {
            return request.execute();
        } catch (RestClientResponseException ex) {
            throw new ResponseStatusException(toApplicationStatus(ex),
                    "Finnhub rejected the request.");
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Finnhub is not available.");
        }
    }

    private HttpStatus toApplicationStatus(RestClientResponseException ex) {
        int status = ex.getStatusCode().value();
        if (status == 400 || status == 401 || status == 403 || status == 404 || status == 429) {
            return HttpStatus.valueOf(status);
        }
        return HttpStatus.BAD_GATEWAY;
    }

    private void ensureConfigured() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Finnhub API key is not configured.");
        }
    }

    private MarketSymbolSearchItemResponse mapSearchItem(Map<String, Object> item) {
        return new MarketSymbolSearchItemResponse(
                stringValue(item, "description"),
                stringValue(item, "displaySymbol"),
                stringValue(item, "symbol"),
                stringValue(item, "type"));
    }

    private MarketNewsResponse mapNews(Map<String, Object> item) {
        return new MarketNewsResponse(
                stringValue(item, "category"),
                longValue(item, "datetime"),
                stringValue(item, "headline"),
                longValue(item, "id"),
                stringValue(item, "image"),
                stringValue(item, "related"),
                stringValue(item, "source"),
                stringValue(item, "summary"),
                stringValue(item, "url"));
    }

    private String normalizeSymbol(String value) {
        if (value == null || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Symbol is required.");
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizeExchange(String value) {
        if (value == null || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exchange is required.");
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizeOptionalExchange(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizeNewsCategory(String value) {
        if (value == null || value.isBlank()) {
            return "general";
        }

        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if (!List.of("general", "forex", "crypto", "merger").contains(normalized)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "News category must be general, forex, crypto or merger.");
        }
        return normalized;
    }

    private String normalizeDate(String value, String paramName) {
        if (value == null || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, paramName + " date is required.");
        }

        try {
            return LocalDate.parse(value.trim()).toString();
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    paramName + " must use YYYY-MM-DD format.");
        }
    }

    private void addQueryParam(UriBuilder uriBuilder, String name, String value) {
        if (value != null && !value.isBlank()) {
            uriBuilder.queryParam(name, value);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> listValue(Object value) {
        if (!(value instanceof List<?> list)) {
            return List.of();
        }
        return list.stream()
                .filter(Map.class::isInstance)
                .map(item -> (Map<String, Object>) item)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> mapValue(Object value) {
        if (value instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        return Map.of();
    }

    private String stringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    private BigDecimal decimalValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null || value.toString().isBlank()) {
            return null;
        }
        return new BigDecimal(value.toString());
    }

    private Long longValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null || value.toString().isBlank()) {
            return null;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        return Long.parseLong(value.toString());
    }

    private Integer integerValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null || value.toString().isBlank()) {
            return null;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        return Integer.parseInt(value.toString());
    }

    private int intValue(Map<String, Object> map, String key) {
        Integer value = integerValue(map, key);
        return value != null ? value : 0;
    }

    private Boolean booleanValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Boolean bool) {
            return bool;
        }
        return value != null ? Boolean.valueOf(value.toString()) : null;
    }

    @FunctionalInterface
    private interface QueryBuilder {
        void apply(UriBuilder uriBuilder);
    }

    @FunctionalInterface
    private interface ProviderRequest<T> {
        T execute();
    }
}
