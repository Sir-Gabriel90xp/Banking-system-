package com.bankingsystem.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bankingsystem.common.AuditContext;
import com.bankingsystem.dto.loan.LoanRequest;
import com.bankingsystem.dto.loan.LoanResponse;
import com.bankingsystem.entities.Customer;
import com.bankingsystem.entities.Loan;
import com.bankingsystem.entities.enums.LoanStatus;
import com.bankingsystem.events.audit.AuditEvent;
import com.bankingsystem.events.audit.AuditEventPublisher;
import com.bankingsystem.mapper.LoanMapper;
import com.bankingsystem.repositories.CustomerRepository;
import com.bankingsystem.repositories.LoanRepository;
import com.bankingsystem.security.AppUserPrincipal;

import lombok.RequiredArgsConstructor;

/**
 * RF-05 (Préstamos) / CU-08 (solicitud) / CU-09 (aprobación-rechazo).
 *
 * ADR-009: la tasa de interés es fija y global, configurada en
 * application.yml (loan.interest-rate.annual). No viaja en el request
 * ni se decide al momento de aprobar.
 */
@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final LoanMapper loanMapper;
    private final AuditEventPublisher auditEventPublisher;
    private final AuditContext auditContext;

    @Value("${app.loan.interest-rate.annual}")
    private BigDecimal annualInterestRate;

    // ---------------------------------------------------------------
    // CU-08: Solicitud de préstamo
    // ---------------------------------------------------------------

    @Transactional
    public LoanResponse requestLoan(LoanRequest request, Authentication authentication) {
        Customer customer = resolveAuthenticatedCustomer(authentication);

        BigDecimal monthlyPayment = calculateMonthlyPayment(
                request.getAmount(), annualInterestRate, request.getTermMonths());

        Loan loan = new Loan();
        loan.setAmount(request.getAmount());
        loan.setInterestRate(annualInterestRate);
        loan.setTermMonths(request.getTermMonths());
        loan.setMonthlyPayment(monthlyPayment);
        loan.setStatus(LoanStatus.PENDING);
        loan.setCustomer(customer);

        Loan saved = loanRepository.save(loan);
        auditEventPublisher.publish(AuditEvent.of(
                auditContext.resolveUserId(authentication),
                "LOAN_REQUESTED",
                "Loan",
                saved.getId(),
                auditContext.resolveClientIp()));
        return loanMapper.toResponse(saved);
    }

    // ---------------------------------------------------------------
    // Consultas
    // ---------------------------------------------------------------

    @Transactional(readOnly = true)
    public Page<LoanResponse> list(UUID customerId, LoanStatus status, Pageable pageable,
                                    Authentication authentication) {
        UUID effectiveCustomerId = restrictToOwnCustomerIfNeeded(customerId, authentication);

        Page<Loan> page;
        if (effectiveCustomerId != null && status != null) {
            page = loanRepository.findByCustomerIdAndStatus(effectiveCustomerId, status, pageable);
        } else if (effectiveCustomerId != null) {
            page = loanRepository.findByCustomerId(effectiveCustomerId, pageable);
        } else if (status != null) {
            page = loanRepository.findByStatus(status, pageable);
        } else {
            page = loanRepository.findAll(pageable);
        }

        return page.map(loanMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public LoanResponse getById(UUID id, Authentication authentication) {
        Loan loan = findLoanOrThrow(id);
        checkOwnership(loan, authentication);
        return loanMapper.toResponse(loan);
    }

    // ---------------------------------------------------------------
    // CU-09: Aprobación / Rechazo (actor: Employee)
    // ---------------------------------------------------------------

    @Transactional
    public LoanResponse approve(UUID id) {
        Loan loan = findLoanOrThrow(id);
        assertPending(loan);
        loan.setStatus(LoanStatus.APPROVED);
        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Transactional
    public LoanResponse reject(UUID id) {
        Loan loan = findLoanOrThrow(id);
        assertPending(loan);
        loan.setStatus(LoanStatus.REJECTED);
        return loanMapper.toResponse(loanRepository.save(loan));
    }

    // ---------------------------------------------------------------
    // Helpers privados
    // ---------------------------------------------------------------

    private Loan findLoanOrThrow(UUID id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found."));
    }

    private void assertPending(Loan loan) {
        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.valueOf(422),
                    "Only loans in PENDING status can be approved or rejected.");
        }
    }

    /**
     * RF-05.3: cuota mensual por amortización francesa estándar.
     * M = P * r / (1 - (1 + r)^-n), con r = tasa anual / 12.
     *
     * Se calcula con double por simplicidad (proyección informativa, no
     * es una operación de saldo real como debit/credit) y se redondea a
     * 2 decimales. Si se requiere precisión financiera estricta más
     * adelante, migrar a BigDecimal puro (registrar como ADR aparte).
     */
    private BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal annualRate, int termMonths) {
        double p = amount.doubleValue();
        double r = annualRate.doubleValue() / 12.0;
        double n = termMonths;

        double monthly = (r == 0.0)
                ? p / n
                : p * r / (1 - Math.pow(1 + r, -n));

        return BigDecimal.valueOf(monthly).setScale(2, RoundingMode.HALF_UP);
    }

    private Customer resolveAuthenticatedCustomer(Authentication authentication) {
        UUID currentUserId = ((AppUserPrincipal) authentication.getPrincipal()).getId();
        return customerRepository.findByUser_Id(currentUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "The authenticated user has no associated customer."));
    }

    /**
     * Si el actor es CUSTOMER, fuerza a que solo pueda listar sus propios
     * préstamos, ignorando cualquier customerId distinto pasado por query
     * param (mismo criterio que AccountService.checkOwnership).
     */
    private UUID restrictToOwnCustomerIfNeeded(UUID requestedCustomerId, Authentication authentication) {
        boolean isCustomerRole = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .anyMatch(role -> "ROLE_CUSTOMER".equals(role));

        if (!isCustomerRole) {
            return requestedCustomerId;
        }

        return resolveAuthenticatedCustomer(authentication).getId();
    }

    private void checkOwnership(Loan loan, Authentication authentication) {
        boolean isCustomerRole = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .anyMatch(role -> "ROLE_CUSTOMER".equals(role));

        if (!isCustomerRole) {
            return;
        }

        UUID currentUserId = ((AppUserPrincipal) authentication.getPrincipal()).getId();
        boolean owns = customerRepository.findByUser_Id(currentUserId)
                .map(c -> c.getId().equals(loan.getCustomer().getId()))
                .orElse(false);

        if (!owns) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this loan.");
        }
    }
}