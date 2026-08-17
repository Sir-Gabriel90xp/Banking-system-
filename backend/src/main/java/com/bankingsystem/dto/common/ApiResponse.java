package com.bankingsystem.dto.common;

import java.time.Instant;
import java.util.List;

/**
 * Envoltorio de respuesta estándar de la API (ver 04_api_design.md).
 *
 * Respuesta exitosa: success=true, data poblado, errors=null.
 * Respuesta con error: success=false, data=null, errors poblado.
 *
 * Nota: por simplicidad no se usa @JsonInclude(NON_NULL) todavía (el
 * campo no usado viaja como null en el JSON en vez de omitirse). Con
 * Spring Boot 4 / Jackson 3 el groupId de las anotaciones puede haber
 * cambiado (ver ADR-006); se deja como mejora futura una vez confirmado
 * el import correcto en el proyecto real.
 */
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        List<String> errors,
        Instant timestamp) {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null, Instant.now());
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, null, Instant.now());
    }

    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, message, null, null, Instant.now());
    }

    public static ApiResponse<Void> error(String message, List<String> errors) {
        return new ApiResponse<>(false, message, null, errors, Instant.now());
    }
}