package com.bankingsystem.services;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bankingsystem.common.AuditContext;
import com.bankingsystem.dto.account.AccountRequest;
import com.bankingsystem.dto.account.AccountResponse;
import com.bankingsystem.dto.account.BalanceResponse;
import com.bankingsystem.entities.Account;
import com.bankingsystem.entities.Customer;
import com.bankingsystem.entities.enums.AccountStatus;
import com.bankingsystem.entities.enums.AccountType;
import com.bankingsystem.events.audit.AuditEvent;
import com.bankingsystem.events.audit.AuditEventPublisher;
import com.bankingsystem.mapper.AccountMapper;
import com.bankingsystem.repositories.AccountRepository;
import com.bankingsystem.repositories.CustomerRepository;
import com.bankingsystem.security.AppUserPrincipal;

import lombok.RequiredArgsConstructor;

/**
 * Cubre RF-03 completo, CU-04, CU-05, CU-06 y RF-07.1 (auditoría de
 * creación/bloqueo/activación de cuentas).
 *
 * currency se hardcodea a "USD" al crear (ADR pendiente de confirmar
 * explícitamente; el alcance del proyecto excluye multidivisa según
 * 01_requirements.md).
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private static final String DEFAULT_CURRENCY = "USD";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;
    private final AuditEventPublisher auditEventPublisher;
    private final AuditContext auditContext;

    // ---------------------------------------------------------------
    // CU-04: Creación de cuenta
    // ---------------------------------------------------------------

    @Transactional
    public AccountResponse createAccount(AccountRequest request, Authentication authentication) {
        Customer customer = resolveCustomerForCreation(request, authentication);
        assertAccountTypeAvailable(customer.getId(), request.getAccountType());
        BigDecimal initialBalance = isCustomer(authentication)
                ? BigDecimal.ZERO
                : resolveInitialBalance(request.getInitialBalance());

        Account account = new Account();
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(initialBalance);
        account.setCurrency(DEFAULT_CURRENCY);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCustomer(customer);

        Account saved = accountRepository.save(account);

        auditEventPublisher.publish(AuditEvent.of(
                auditContext.resolveUserId(authentication),
                "ACCOUNT_CREATED",
                "Account",
                saved.getId(),
                auditContext.resolveClientIp()));

        return accountMapper.toResponse(saved);
    }

    // ---------------------------------------------------------------
    // Consultas de cuentas
    // ---------------------------------------------------------------

    @Transactional(readOnly = true)
    public Page<AccountResponse> listAccounts(Pageable pageable, AccountStatus status, UUID customerId,
            Authentication authentication) {
        UUID effectiveCustomerId = resolveCustomerScope(customerId, authentication);
        Page<Account> page;

        if (effectiveCustomerId != null && status != null) {
            page = accountRepository.findByCustomerIdAndStatus(effectiveCustomerId, status, pageable);
        } else if (effectiveCustomerId != null) {
            page = accountRepository.findByCustomerId(effectiveCustomerId, pageable);
        } else if (status != null) {
            page = accountRepository.findByStatus(status, pageable);
        } else {
            page = accountRepository.findAll(pageable);
        }

        return page.map(accountMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccountById(UUID id, Authentication authentication) {
        Account account = findAccountOrThrow(id);
        checkOwnership(account, authentication);
        return accountMapper.toResponse(account);
    }

    // ---------------------------------------------------------------
    // CU-06: Bloqueo / Activación (RF-03.3, RF-03.4, RF-08.3)
    // ---------------------------------------------------------------

    @Transactional
    public AccountResponse block(UUID id, Authentication authentication) {
        Account account = findAccountOrThrow(id);

        if (account.getStatus() == AccountStatus.BLOCKED) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), "Account is already blocked.");
        }
        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), "A closed account cannot be blocked.");
        }

        account.setStatus(AccountStatus.BLOCKED);
        Account saved = accountRepository.save(account);

        auditEventPublisher.publish(AuditEvent.of(
                auditContext.resolveUserId(authentication),
                "ACCOUNT_BLOCKED",
                "Account",
                saved.getId(),
                auditContext.resolveClientIp()));

        return accountMapper.toResponse(saved);
    }

    @Transactional
    public AccountResponse activate(UUID id, Authentication authentication) {
        Account account = findAccountOrThrow(id);

        if (account.getStatus() != AccountStatus.BLOCKED) {
            throw new ResponseStatusException(HttpStatus.valueOf(422),
                    "Only a blocked account can be activated.");
        }

        account.setStatus(AccountStatus.ACTIVE);
        Account saved = accountRepository.save(account);

        auditEventPublisher.publish(AuditEvent.of(
                auditContext.resolveUserId(authentication),
                "ACCOUNT_ACTIVATED",
                "Account",
                saved.getId(),
                auditContext.resolveClientIp()));

        return accountMapper.toResponse(saved);
    }

    // ---------------------------------------------------------------
    // Gestión de balances (RF-03.2, RF-03.5 / RN-01, RN-06)
    // ---------------------------------------------------------------

    @Transactional(readOnly = true)
    public BalanceResponse getBalance(UUID id, Authentication authentication) {
        Account account = findAccountOrThrow(id);
        checkOwnership(account, authentication);
        return accountMapper.toBalanceResponse(account);
    }

    @Transactional
    public Account debit(UUID accountId, BigDecimal amount) {
        Account account = findAccountOrThrow(accountId);
        assertActive(account);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), "Insufficient balance.");
        }

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public Account credit(UUID accountId, BigDecimal amount) {
        Account account = findAccountOrThrow(accountId);
        assertActive(account);

        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public Account blockAccount(UUID accountId) {
        Account account = findAccountOrThrow(accountId);

        if (account.getStatus() == AccountStatus.BLOCKED) {
            return account;
        }
        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), "A closed account cannot be blocked.");
        }

        account.setStatus(AccountStatus.BLOCKED);
        Account saved = accountRepository.save(account);

        auditEventPublisher.publish(AuditEvent.of(
                null,
                "ACCOUNT_BLOCKED",
                "Account",
                saved.getId(),
                null));

        return saved;
    }

    // ---------------------------------------------------------------
    // Helpers privados
    // ---------------------------------------------------------------

    private Account findAccountOrThrow(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found."));
    }

    private void assertActive(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), "Account is not active (RN-06).");
        }
    }

    private BigDecimal resolveInitialBalance(BigDecimal initialBalance) {
        if (initialBalance == null) {
            return BigDecimal.ZERO;
        }
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Initial balance cannot be negative.");
        }
        return initialBalance;
    }

    private Customer resolveCustomerForCreation(AccountRequest request, Authentication authentication) {
        if (isCustomer(authentication)) {
            UUID currentUserId = ((AppUserPrincipal) authentication.getPrincipal()).getId();
            Customer currentCustomer = customerRepository.findByUser_Id(currentUserId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.FORBIDDEN, "The authenticated user is not linked to a customer."));

            if (request.getCustomerId() != null && !request.getCustomerId().equals(currentCustomer.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only create accounts for yourself.");
            }

            return currentCustomer;
        }

        if (request.getCustomerId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer id is required.");
        }

        return customerRepository.findByIdAndDeletedFalse(request.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found."));
    }

    private void assertAccountTypeAvailable(UUID customerId, AccountType accountType) {
        if (accountRepository.existsByCustomerIdAndAccountTypeAndDeletedFalse(customerId, accountType)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "The customer already has an account of type " + accountType + ".");
        }
    }

    private UUID resolveCustomerScope(UUID requestedCustomerId, Authentication authentication) {
        if (!isCustomer(authentication)) {
            return requestedCustomerId;
        }

        UUID currentUserId = ((AppUserPrincipal) authentication.getPrincipal()).getId();
        Customer currentCustomer = customerRepository.findByUser_Id(currentUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "The authenticated user is not linked to a customer."));

        if (requestedCustomerId != null && !requestedCustomerId.equals(currentCustomer.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to these accounts.");
        }

        return currentCustomer.getId();
    }

    private void checkOwnership(Account account, Authentication authentication) {
        if (!isCustomer(authentication)) {
            return;
        }

        UUID currentUserId = ((AppUserPrincipal) authentication.getPrincipal()).getId();

        boolean owns = customerRepository.findByUser_Id(currentUserId)
                .map(customer -> customer.getId().equals(account.getCustomer().getId()))
                .orElse(false);

        if (!owns) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this account.");
        }
    }

    private boolean isCustomer(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .anyMatch(role -> "ROLE_CUSTOMER".equals(role));
    }

    /**
     * RF-03.6: número de cuenta único. Genera 10 dígitos numéricos y
     * reintenta en el caso (estadísticamente muy raro) de colisión.
     */
    private String generateUniqueAccountNumber() {
        String candidate;
        do {
            candidate = String.format("%010d", RANDOM.nextLong(0, 10_000_000_000L));
        } while (accountRepository.existsByAccountNumber(candidate));
        return candidate;
    }
}
