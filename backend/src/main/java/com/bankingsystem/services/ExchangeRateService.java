package com.bankingsystem.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriBuilder;

import com.bankingsystem.dto.exchange.CurrencyResponse;
import com.bankingsystem.dto.exchange.ExchangeConversionResponse;
import com.bankingsystem.dto.exchange.ExchangeProviderResponse;
import com.bankingsystem.dto.exchange.ExchangeRateProviderContributionResponse;
import com.bankingsystem.dto.exchange.ExchangeRateResponse;

/**
 * Cliente interno para Frankfurter (https://api.frankfurter.dev).
 *
 * El frontend consume nuestro backend, no el proveedor externo directamente.
 * Eso mantiene el contrato de la app estable y deja la URL del proveedor como
 * configuracion de entorno.
 */
@Service
public class ExchangeRateService {

    private static final ParameterizedTypeReference<List<Map<String, Object>>> MAP_LIST_TYPE =
            new ParameterizedTypeReference<>() {};

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    public ExchangeRateService(
            RestClient.Builder restClientBuilder,
            @Value("${app.exchange-rates.frankfurter-base-url}") String frankfurterBaseUrl) {
        this.restClient = restClientBuilder.baseUrl(frankfurterBaseUrl).build();
    }

    public List<ExchangeRateResponse> getRates(
            String base,
            String quotes,
            String date,
            String from,
            String to,
            String group,
            String providers,
            boolean includeProviders) {

        String normalizedBase = normalizeOptionalCurrency(base, "base");
        String normalizedQuotes = normalizeCurrencyList(quotes, "quotes");
        String normalizedDate = normalizeDate(date, "date");
        String normalizedFrom = normalizeDate(from, "from");
        String normalizedTo = normalizeDate(to, "to");
        String normalizedGroup = normalizeGroup(group);
        String normalizedProviders = normalizeProviderList(providers);

        List<Map<String, Object>> response = getMapList("/v2/rates", uriBuilder -> {
            addQueryParam(uriBuilder, "base", normalizedBase);
            addQueryParam(uriBuilder, "quotes", normalizedQuotes);
            addQueryParam(uriBuilder, "date", normalizedDate);
            addQueryParam(uriBuilder, "from", normalizedFrom);
            addQueryParam(uriBuilder, "to", normalizedTo);
            addQueryParam(uriBuilder, "group", normalizedGroup);
            addQueryParam(uriBuilder, "providers", normalizedProviders);
            if (includeProviders) {
                uriBuilder.queryParam("expand", "providers");
            }
        });

        return response.stream()
                .map(this::mapRate)
                .toList();
    }

    public ExchangeRateResponse getRate(String base, String quote, String date, String providers) {
        String normalizedBase = normalizeCurrency(base, "base");
        String normalizedQuote = normalizeCurrency(quote, "quote");
        String normalizedDate = normalizeDate(date, "date");
        String normalizedProviders = normalizeProviderList(providers);

        Map<String, Object> response = execute(() -> restClient.get()
                .uri(uriBuilder -> {
                    UriBuilder builder = uriBuilder.path("/v2/rate/{base}/{quote}");
                    addQueryParam(builder, "date", normalizedDate);
                    addQueryParam(builder, "providers", normalizedProviders);
                    return builder.build(normalizedBase, normalizedQuote);
                })
                .retrieve()
                .body(MAP_TYPE));

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Exchange rate provider returned an empty response.");
        }

        return mapRate(response);
    }

    public ExchangeConversionResponse convert(
            BigDecimal amount,
            String base,
            String quote,
            String date,
            String providers) {

        if (amount == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount is required.");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount cannot be negative.");
        }

        String normalizedBase = normalizeCurrency(base, "base");
        String normalizedQuote = normalizeCurrency(quote, "quote");

        if (normalizedBase.equals(normalizedQuote)) {
            BigDecimal normalizedAmount = amount.setScale(2, RoundingMode.HALF_UP);
            return new ExchangeConversionResponse(
                    date != null && !date.isBlank() ? normalizeDate(date, "date") : LocalDate.now().toString(),
                    normalizedBase,
                    normalizedQuote,
                    amount,
                    BigDecimal.ONE,
                    normalizedAmount);
        }

        ExchangeRateResponse rate = getRate(normalizedBase, normalizedQuote, date, providers);
        BigDecimal convertedAmount = amount.multiply(rate.rate()).setScale(2, RoundingMode.HALF_UP);

        return new ExchangeConversionResponse(
                rate.date(),
                rate.base(),
                rate.quote(),
                amount,
                rate.rate(),
                convertedAmount);
    }

    public List<CurrencyResponse> getCurrencies(String scope) {
        String normalizedScope = normalizeScope(scope);

        List<Map<String, Object>> response = getMapList("/v2/currencies", uriBuilder ->
                addQueryParam(uriBuilder, "scope", normalizedScope));

        return response.stream()
                .map(this::mapCurrency)
                .toList();
    }

    public CurrencyResponse getCurrency(String code) {
        String normalizedCode = normalizeCurrency(code, "code");

        Map<String, Object> response = execute(() -> restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v2/currency/{code}").build(normalizedCode))
                .retrieve()
                .body(MAP_TYPE));

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Exchange rate provider returned an empty response.");
        }

        return mapCurrency(response);
    }

    public List<ExchangeProviderResponse> getProviders() {
        List<Map<String, Object>> response = getMapList("/v2/providers", uriBuilder -> {});

        return response.stream()
                .map(this::mapProvider)
                .toList();
    }

    private List<Map<String, Object>> getMapList(String path, Consumer<UriBuilder> queryParams) {
        List<Map<String, Object>> response = execute(() -> restClient.get()
                .uri(uriBuilder -> {
                    UriBuilder builder = uriBuilder.path(path);
                    queryParams.accept(builder);
                    return builder.build();
                })
                .retrieve()
                .body(MAP_LIST_TYPE));

        return response != null ? response : List.of();
    }

    private <T> T execute(Supplier<T> request) {
        try {
            return request.get();
        } catch (RestClientResponseException ex) {
            HttpStatus status = toApplicationStatus(ex);
            throw new ResponseStatusException(status, "Exchange rate provider rejected the request.");
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Exchange rate provider is not available.");
        }
    }

    private HttpStatus toApplicationStatus(RestClientResponseException ex) {
        int status = ex.getStatusCode().value();
        if (status == 400 || status == 404 || status == 422) {
            return HttpStatus.valueOf(status);
        }
        return HttpStatus.BAD_GATEWAY;
    }

    private ExchangeRateResponse mapRate(Map<?, ?> map) {
        return new ExchangeRateResponse(
                stringValue(map, "date"),
                stringValue(map, "base"),
                stringValue(map, "quote"),
                decimalValue(map, "rate"),
                mapProviderContributions(map.get("providers")));
    }

    private CurrencyResponse mapCurrency(Map<?, ?> map) {
        return new CurrencyResponse(
                stringValue(map, "iso_code"),
                stringValue(map, "iso_numeric"),
                stringValue(map, "name"),
                stringValue(map, "symbol"),
                stringValue(map, "start_date"));
    }

    private ExchangeProviderResponse mapProvider(Map<?, ?> map) {
        return new ExchangeProviderResponse(
                stringValue(map, "key"),
                stringValue(map, "name"),
                stringValue(map, "country_code"),
                stringValue(map, "rate_type"),
                stringValue(map, "pivot_currency"));
    }

    private List<ExchangeRateProviderContributionResponse> mapProviderContributions(Object providers) {
        if (!(providers instanceof List<?> providerList)) {
            return List.of();
        }

        return providerList.stream()
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .map(provider -> new ExchangeRateProviderContributionResponse(
                        stringValue(provider, "key"),
                        stringValue(provider, "date"),
                        decimalValue(provider, "rate"),
                        booleanValue(provider, "excluded")))
                .toList();
    }

    private String normalizeOptionalCurrency(String value, String paramName) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return normalizeCurrency(value, paramName);
    }

    private String normalizeCurrency(String value, String paramName) {
        if (value == null || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, paramName + " currency is required.");
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT);
        if (!normalized.matches("[A-Z]{3}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    paramName + " must be a valid ISO 4217 currency code.");
        }

        return normalized;
    }

    private String normalizeCurrencyList(String value, String paramName) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return List.of(value.split(",")).stream()
                .map(item -> normalizeCurrency(item, paramName))
                .reduce((left, right) -> left + "," + right)
                .orElse(null);
    }

    private String normalizeProviderList(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return List.of(value.split(",")).stream()
                .map(item -> item.trim().toUpperCase(Locale.ROOT))
                .filter(item -> !item.isBlank())
                .reduce((left, right) -> left + "," + right)
                .orElse(null);
    }

    private String normalizeDate(String value, String paramName) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return LocalDate.parse(value.trim()).toString();
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    paramName + " must use YYYY-MM-DD format.");
        }
    }

    private String normalizeGroup(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if (!normalized.equals("week") && !normalized.equals("month")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "group must be week or month.");
        }

        return normalized;
    }

    private String normalizeScope(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if (!normalized.equals("all")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "scope must be all when provided.");
        }

        return normalized;
    }

    private void addQueryParam(UriBuilder uriBuilder, String name, String value) {
        if (value != null && !value.isBlank()) {
            uriBuilder.queryParam(name, value);
        }
    }

    private String stringValue(Map<?, ?> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    private BigDecimal decimalValue(Map<?, ?> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal decimal) {
            return decimal;
        }
        return new BigDecimal(value.toString());
    }

    private Boolean booleanValue(Map<?, ?> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean bool) {
            return bool;
        }
        return Boolean.valueOf(value.toString());
    }
}
