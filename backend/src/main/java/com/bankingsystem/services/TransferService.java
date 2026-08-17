package com.bankingsystem.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.bankingsystem.dto.account.transfer.TransferRequest;
import com.bankingsystem.dto.account.transfer.TransferResponse;
import com.bankingsystem.entities.Account;
import com.bankingsystem.entities.Customer;
import com.bankingsystem.entities.Transfer;
import com.bankingsystem.entities.enums.AccountStatus;
import com.bankingsystem.events.audit.AuditEvent;
import com.bankingsystem.events.audit.AuditEventPublisher;
import com.bankingsystem.mapper.TransferMapper;
import com.bankingsystem.repositories.AccountRepository;
import com.bankingsystem.repositories.CustomerRepository;
import com.bankingsystem.repositories.TransferRepository;
import com.bankingsystem.security.AppUserPrincipal;

import lombok.RequiredArgsConstructor;

/**
 * Cubre RF-04.1 a RF-04.5 y CU-07.
 *
 * RF-04.5 (auditoría) ya integrado: se publica un AuditEvent tras
 * completar la transferencia. El paso 6 de CU-07 (evento para
 * evaluación de fraude) sigue como TODO — corresponde al módulo de
 * Fraude, todavía no implementado.
 */
@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final TransferMapper transferMapper;
    private final AuditEventPublisher auditEventPublisher;

    private static final String STATUS_COMPLETED = "COMPLETED";

    @Value("${app.transfer.daily-limit:10}")
    private int dailyTransferLimit;

    @Transactional
    public TransferResponse createTransfer(TransferRequest request, Authentication authentication) {

        Account origin = resolveSourceAccount(request.sourceAccount(), authentication);
        Account destination = resolveDestinationAccount(request);

        if (origin.getId().equals(destination.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.valueOf(422),
                    "Source and destination accounts must be different."
            );
        }

        assertDailyTransferLimit(origin);

        accountService.debit(origin.getId(), request.amount());
        accountService.credit(destination.getId(), request.amount());

        Transfer transfer = new Transfer();
        transfer.setOriginAccount(origin);
        transfer.setDestinationAccount(destination);
        transfer.setAmount(request.amount());
        transfer.setStatus(STATUS_COMPLETED);

        Transfer saved = transferRepository.save(transfer);

        auditEventPublisher.publish(AuditEvent.of(
                resolveUserId(authentication),
                "TRANSFER_CREATED",
                "Transfer",
                saved.getId(),
                resolveClientIp()));

        // TODO (Fase 4 - Fraude): publicar evento para evaluación de fraude (CU-07, paso 6).

        return transferMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<TransferResponse> getHistory(Pageable pageable, UUID accountId, String status,
            Authentication authentication) {
        if (accountId != null) {
            accountService.getAccountById(accountId, authentication);
        }

        UUID customerId = resolveCustomerScope(authentication);
        Page<Transfer> page = transferRepository.findHistory(accountId, customerId, status, pageable);
        return page.map(transferMapper::toResponse);
    }

    // ---------------------------------------------------------------
    // Helpers privados (compartidos por todos los Services que auditen)
    // ---------------------------------------------------------------

    private Account resolveSourceAccount(UUID sourceAccountId, Authentication authentication) {
        Account account = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Source account not found."));

        assertUsableAccount(account, "Source account is not active.");
        checkSourceOwnership(account, authentication);
        return account;
    }

    private Account resolveDestinationAccount(TransferRequest request) {
        String destinationNumber = normalizeAccountNumber(request.destinationAccountNumber());

        if (request.destinationAccount() != null && destinationNumber != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Use either destinationAccount or destinationAccountNumber, not both.");
        }

        Account account;
        if (request.destinationAccount() != null) {
            account = accountRepository.findById(request.destinationAccount())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Destination account not found."));
        } else if (destinationNumber != null) {
            account = accountRepository.findByAccountNumber(destinationNumber)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Destination account not found."));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Destination account or destination account number is required.");
        }

        assertUsableAccount(account, "Destination account is not active.");
        return account;
    }

    private void assertUsableAccount(Account account, String inactiveMessage) {
        if (account.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        }
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.valueOf(422), inactiveMessage);
        }
    }

    private void checkSourceOwnership(Account account, Authentication authentication) {
        if (!isCustomer(authentication)) {
            return;
        }

        UUID currentUserId = ((AppUserPrincipal) authentication.getPrincipal()).getId();
        boolean owns = customerRepository.findByUser_Id(currentUserId)
                .map(customer -> customer.getId().equals(account.getCustomer().getId()))
                .orElse(false);

        if (!owns) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this source account.");
        }
    }

    private void assertDailyTransferLimit(Account origin) {
        if (dailyTransferLimit <= 0) {
            return;
        }

        Instant since = Instant.now().minus(24, ChronoUnit.HOURS);
        long usedTransfers = transferRepository.countByOriginAccountIdAndCreatedAtAfter(origin.getId(), since);
        if (usedTransfers >= dailyTransferLimit) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Daily transfer limit reached for this account.");
        }
    }

    private String normalizeAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return null;
        }
        return accountNumber.trim().replaceAll("\\s+", "");
    }

    private UUID resolveUserId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AppUserPrincipal principal)) {
            return null;
        }
        return principal.getId();
    }

    private UUID resolveCustomerScope(Authentication authentication) {
        if (!isCustomer(authentication)) {
            return null;
        }

        UUID currentUserId = ((AppUserPrincipal) authentication.getPrincipal()).getId();
        Customer customer = customerRepository.findByUser_Id(currentUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "The authenticated user is not linked to a customer."));

        return customer.getId();
    }

    private boolean isCustomer(Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .anyMatch(role -> "ROLE_CUSTOMER".equals(role));
    }

    private String resolveClientIp() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) {
            return null;
        }

        String forwardedFor = attrs.getRequest().getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        return attrs.getRequest().getRemoteAddr();
    }
}
