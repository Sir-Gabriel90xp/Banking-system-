package com.bankingsystem.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.auth.customer.CustomerRequest;
import com.bankingsystem.dto.auth.customer.CustomerResponse;
import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.services.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Endpoints de Customers (11_endpoints.md, módulo 3).
 * Controller: solo recibe la solicitud, valida parámetros básicos e invoca
 * al Service (02_architecture.md); no contiene lógica de negocio.
 *
 * NOTA sobre ApiResponse: se asume que dto/common/ApiResponse.java (creado en
 * la Sesión 7 para Auth) expone factory estáticos ApiResponse.success(data, message)
 * y ApiResponse.success(message) para 204/sin data. Si la firma real es distinta,
 * ajustar solo estas llamadas, no el resto del controller.
 *
 * NOTA sobre roles: CU-03 exige rol EMPLOYEE o ADMIN para registrar/editar/eliminar
 * clientes. La consulta (GET) se deja abierta a cualquier usuario autenticado por
 * no estar restringida explícitamente en la documentación; ajustar si corresponde
 * restringirla también (por ejemplo, si un CUSTOMER no debería listar otros clientes).
 * Requiere @EnableMethodSecurity habilitado en SecurityConfig para que @PreAuthorize
 * tenga efecto.
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<CustomerResponse>> create(
            @Valid @RequestBody CustomerRequest request,
            Authentication authentication) {

        CustomerResponse created = customerService.create(request, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Customer created successfully."));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<Page<CustomerResponse>>> list(
            @RequestParam(required = false) String search,
            Pageable pageable) {

        Page<CustomerResponse> customers = customerService.list(search, pageable);
        return ResponseEntity.ok(
                ApiResponse.success(customers, "Customers retrieved successfully."));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<CustomerResponse>> getById(@PathVariable UUID id) {
        CustomerResponse customer = customerService.getById(id);
        return ResponseEntity.ok(
                ApiResponse.success(customer, "Customer retrieved successfully."));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<CustomerResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse updated = customerService.update(id, request);
        return ResponseEntity.ok(
                ApiResponse.success(updated, "Customer updated successfully."));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        customerService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Customer deleted successfully."));
    }
}
