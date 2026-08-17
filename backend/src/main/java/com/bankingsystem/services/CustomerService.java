package com.bankingsystem.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bankingsystem.dto.account.AccountRequest;
import com.bankingsystem.dto.auth.customer.CustomerRequest;
import com.bankingsystem.dto.auth.customer.CustomerResponse;
import com.bankingsystem.entities.AppUser;
import com.bankingsystem.entities.Customer;
import com.bankingsystem.entities.Role;
import com.bankingsystem.entities.enums.AccountType;
import com.bankingsystem.entities.enums.CustomerStatus;
import com.bankingsystem.mapper.CustomerMapper;
import com.bankingsystem.repositories.AppUserRepository;
import com.bankingsystem.repositories.CustomerRepository;
import com.bankingsystem.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

/**
 * Lógica de negocio de Customers (RF-02, CU-03, CU-05).
 *
 * Manejo de errores: se sigue el mismo criterio provisional de ADR-008
 * (ResponseStatusException + GlobalExceptionHandler ya existente), en vez de
 * crear ya la jerarquía de excepciones de negocio de 02_architecture.md.
 * Cuando esa jerarquía se cree (recomendado al avanzar Fase 3), migrar
 * estos throws a ResourceNotFoundException / BusinessException.
 *
 * TODO (Fase 4 / RF-07): al crear, actualizar o eliminar un Customer, se debe
 * publicar un evento de auditoría vía RabbitMQ (CU-03 paso 4). No implementado
 * todavía porque el módulo de Auditoría/RabbitMQ es posterior en el roadmap.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final String CUSTOMER_ROLE = "CUSTOMER";

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Transactional
    public CustomerResponse create(CustomerRequest request, Authentication authentication) {
        validateUniqueness(request.email(), request.documentNumber(), null);

        Customer customer = customerMapper.toEntity(request);
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setUser(createLoginUserIfRequested(request));

        Customer saved = customerRepository.save(customer);
        createInitialAccountIfRequested(request, saved, authentication);

        return customerMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getById(UUID id) {
        Customer customer = findActiveOrThrow(id);
        return customerMapper.toResponse(customer);
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponse> list(String search, Pageable pageable) {
        return customerRepository.search(search, pageable)
                .map(customerMapper::toResponse);
    }

    @Transactional
    public CustomerResponse update(UUID id, CustomerRequest request) {
        Customer customer = findActiveOrThrow(id);
        validateUniqueness(request.email(), request.documentNumber(), id);

        customerMapper.updateEntityFromRequest(request, customer);

        Customer saved = customerRepository.save(customer);
        return customerMapper.toResponse(saved);
    }

    @Transactional
    public void delete(UUID id) {
        Customer customer = findActiveOrThrow(id);
        customer.setDeleted(true);
        customer.setDeletedAt(Instant.now());
        customerRepository.save(customer);
    }

    private Customer findActiveOrThrow(UUID id) {
        return customerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found."));
    }

    /**
     * RF-02.5 / RN-03: documento de identidad y correo únicos.
     * excludeId permite excluir al propio registro en un update.
     */
    private void validateUniqueness(String email, String documentNumber, UUID excludeId) {
        boolean emailExists = (excludeId == null)
                ? customerRepository.existsByEmailAndDeletedFalse(email)
                : customerRepository.existsByEmailAndDeletedFalseAndIdNot(email, excludeId);

        if (emailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered.");
        }

        boolean documentExists = (excludeId == null)
                ? customerRepository.existsByDocumentNumberAndDeletedFalse(documentNumber)
                : customerRepository.existsByDocumentNumberAndDeletedFalseAndIdNot(documentNumber, excludeId);

        if (documentExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Document number already registered.");
        }
    }

    private AppUser createLoginUserIfRequested(CustomerRequest request) {
        if (!Boolean.TRUE.equals(request.createLoginUser())) {
            return null;
        }

        String username = trimToNull(request.username());
        String password = trimToNull(request.password());

        if (username == null || password == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username and password are required to create a login user.");
        }
        if (password.length() < 6) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Password must have at least 6 characters.");
        }
        if (appUserRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already registered.");
        }
        if (appUserRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Login email already registered.");
        }

        Role customerRole = roleRepository.findByName(CUSTOMER_ROLE)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Customer role is not configured."));

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setEmail(request.email());
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setEnabled(true);
        appUser.setRole(customerRole);

        return appUserRepository.save(appUser);
    }

    private void createInitialAccountIfRequested(CustomerRequest request, Customer customer,
            Authentication authentication) {
        if (!Boolean.TRUE.equals(request.createInitialAccount())) {
            return;
        }

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setCustomerId(customer.getId());
        accountRequest.setAccountType(request.initialAccountType() != null
                ? request.initialAccountType()
                : AccountType.SAVINGS);
        accountRequest.setInitialBalance(request.initialBalance() != null
                ? request.initialBalance()
                : BigDecimal.ZERO);

        accountService.createAccount(accountRequest, authentication);
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}
