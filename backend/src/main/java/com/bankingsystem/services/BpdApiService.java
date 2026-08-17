package com.bankingsystem.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import com.bankingsystem.dto.bpd.BpdAtmLocationResponse;
import com.bankingsystem.dto.bpd.BpdConfirmAccountRequest;
import com.bankingsystem.dto.bpd.BpdConfirmAccountResponse;

@Service
public class BpdApiService {

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {};

    private static final ParameterizedTypeReference<List<Map<String, Object>>> MAP_LIST_TYPE =
            new ParameterizedTypeReference<>() {};

    private final RestClient bpdRestClient;
    private final RestClient tokenRestClient;
    private final String confirmAccountPath;
    private final String atmLocationsPath;
    private final String tokenUrl;
    private final String clientId;
    private final String clientSecret;
    private final String scope;

    private volatile CachedToken cachedToken;

    public BpdApiService(
            RestClient.Builder restClientBuilder,
            @Value("${app.bpd.base-url}") String baseUrl,
            @Value("${app.bpd.confirm-account-path}") String confirmAccountPath,
            @Value("${app.bpd.atm-locations-path}") String atmLocationsPath,
            @Value("${app.bpd.token-url}") String tokenUrl,
            @Value("${app.bpd.client-id}") String clientId,
            @Value("${app.bpd.client-secret}") String clientSecret,
            @Value("${app.bpd.scope}") String scope) {
        this.bpdRestClient = restClientBuilder.baseUrl(baseUrl).build();
        this.tokenRestClient = restClientBuilder.build();
        this.confirmAccountPath = confirmAccountPath;
        this.atmLocationsPath = atmLocationsPath;
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }

    public BpdConfirmAccountResponse confirmAccount(BpdConfirmAccountRequest request) {
        ensureConfigured();

        Map<String, Object> body = Map.of(
                "ConfirmarCuentaReq", Map.of(
                        "documentType", request.documentType().trim().toUpperCase(Locale.ROOT),
                        "documentNumber", removeSeparators(request.documentNumber()),
                        "accountNumber", removeSeparators(request.accountNumber())));

        Map<String, Object> response = executeProviderRequest(() -> bpdRestClient.post()
                .uri(confirmAccountPath + "/confirmarcuenta")
                .header("X-IBM-Client-Id", clientId)
                .headers(headers -> headers.setBearerAuth(resolveAccessToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(MAP_TYPE));

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "BPD returned an empty confirm account response.");
        }

        return new BpdConfirmAccountResponse(
                booleanValue(response, "status"),
                stringValue(response, "message"));
    }

    public List<BpdAtmLocationResponse> getAtmLocations(int page) {
        ensureConfigured();
        if (page < 0 || page > 99) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page must be between 0 and 99.");
        }

        List<Map<String, Object>> response = executeProviderRequest(() -> bpdRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(atmLocationsPath + "/ubicacionesatm")
                        .queryParam("Page", page)
                        .build())
                .header("X-IBM-Client-Id", clientId)
                .headers(headers -> headers.setBearerAuth(resolveAccessToken()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(MAP_LIST_TYPE));

        return response == null
                ? List.of()
                : response.stream().map(this::mapAtmLocation).toList();
    }

    private String resolveAccessToken() {
        CachedToken token = cachedToken;
        if (token != null && token.expiresAt().isAfter(Instant.now().plusSeconds(15))) {
            return token.value();
        }

        synchronized (this) {
            token = cachedToken;
            if (token != null && token.expiresAt().isAfter(Instant.now().plusSeconds(15))) {
                return token.value();
            }

            cachedToken = requestAccessToken();
            return cachedToken.value();
        }
    }

    private CachedToken requestAccessToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        if (scope != null && !scope.isBlank()) {
            form.add("scope", scope);
        }

        Map<String, Object> response;
        try {
            response = tokenRestClient.post()
                    .uri(tokenUrl)
                    .header("X-IBM-Client-Id", clientId)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(form)
                    .retrieve()
                    .body(MAP_TYPE);
        } catch (RestClientResponseException ex) {
            throw new ResponseStatusException(toApplicationStatus(ex),
                    "BPD authentication rejected the configured credentials.");
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "BPD authentication service is not available.");
        }

        String accessToken = stringValue(response, "access_token");
        if (accessToken == null || accessToken.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "BPD authentication did not return an access token.");
        }

        long expiresIn = longValue(response, "expires_in", 300L);
        return new CachedToken(accessToken, Instant.now().plusSeconds(Math.max(30L, expiresIn - 30L)));
    }

    private <T> T executeProviderRequest(ProviderRequest<T> request) {
        try {
            return request.execute();
        } catch (RestClientResponseException ex) {
            throw new ResponseStatusException(toApplicationStatus(ex),
                    "BPD provider rejected the request.");
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "BPD provider is not available.");
        }
    }

    private void ensureConfigured() {
        if (clientId == null || clientId.isBlank() || clientSecret == null || clientSecret.isBlank()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "BPD API credentials are not configured.");
        }
    }

    private HttpStatus toApplicationStatus(RestClientResponseException ex) {
        int status = ex.getStatusCode().value();
        if (status == 400 || status == 401 || status == 404) {
            return HttpStatus.valueOf(status);
        }
        return HttpStatus.BAD_GATEWAY;
    }

    private BpdAtmLocationResponse mapAtmLocation(Map<String, Object> map) {
        return new BpdAtmLocationResponse(
                stringValue(map, "id"),
                stringValue(map, "nombre"),
                stringValue(map, "title"),
                stringValue(map, "Dir_Fis"),
                stringValue(map, "horario"),
                decimalValue(map, "latitude"),
                decimalValue(map, "longitude"));
    }

    private String removeSeparators(String value) {
        return value == null ? null : value.replaceAll("[^A-Za-z0-9]", "");
    }

    private String stringValue(Map<String, Object> map, String key) {
        if (map == null) {
            return null;
        }
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    private boolean booleanValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Boolean bool) {
            return bool;
        }
        return Boolean.parseBoolean(value != null ? value.toString() : "false");
    }

    private BigDecimal decimalValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null || value.toString().isBlank()) {
            return null;
        }
        return new BigDecimal(value.toString());
    }

    private long longValue(Map<String, Object> map, String key, long defaultValue) {
        Object value = map != null ? map.get(key) : null;
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    private record CachedToken(String value, Instant expiresAt) {
    }

    @FunctionalInterface
    private interface ProviderRequest<T> {
        T execute();
    }
}
