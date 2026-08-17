package com.bankingsystem.exceptions;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.bankingsystem.dto.common.ApiResponse;

/**
 * Manejador global de excepciones (02_architecture.md - "Manejo Global de
 * Excepciones"). Si el proyecto ya tiene un GlobalExceptionHandler y
 * excepciones de negocio propias (ResourceNotFoundException,
 * ValidationException, etc.), este archivo debe fusionarse con esas
 * clases en vez de coexistir por separado.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatus(ResponseStatusException ex) {
        HttpStatusCode status = ex.getStatusCode();
        String message = ex.getReason() != null ? ex.getReason() : "Ocurrió un error.";
        return ResponseEntity.status(status).body(ApiResponse.error(message, List.of()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();
        return ResponseEntity.badRequest().body(ApiResponse.error("Error de validación.", errors));
    }
}