package com.bankingsystem.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bankingsystem.dto.account.AccountResponse;
import com.bankingsystem.dto.payment.PaymentRequest;
import com.bankingsystem.dto.payment.PaymentResponse;
import com.bankingsystem.entities.Account;
import com.bankingsystem.entities.Loan;
import com.bankingsystem.entities.Payment;
import com.bankingsystem.entities.enums.LoanStatus;
import com.bankingsystem.mapper.PaymentMapper;
import com.bankingsystem.repositories.CustomerRepository;
import com.bankingsystem.repositories.LoanRepository;
import com.bankingsystem.repositories.PaymentRepository;
import com.bankingsystem.security.AppUserPrincipal;

import lombok.RequiredArgsConstructor;

/**
 * RF-06.1, RF-06.3, CU-10 (pago de cuota de préstamo).
 *
 * ADR-010: un Loan pasa a PAID cuando la cantidad de Payments asociados
 * llega a termMonths. No se valida que cada pago sea exactamente igual
 * a monthlyPayment (el SQL/entidad no lo exige); solo se cuenta cuántos
 * pagos existen.
 *
 * Alcance: NO cubre pago de servicios (RF-06.2) todavía, aunque
 * Payment.loan ya es nullable para eso (decisión de Sesión 4). Queda
 * pendiente para una fase futura.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final PaymentMapper paymentMapper;

    // ---------------------------------------------------------------
    // CU-10: Registro de pago
    // ---------------------------------------------------------------

    @Transactional
    public PaymentResponse registerPayment(PaymentRequest request, Authentication authentication) {
        Loan loan = findLoanOrThrow(request.getLoanId());
        checkLoanOwnership(loan, authentication);
        assertApproved(loan);
        AccountResponse sourceAccount = accountService.getAccountById(request.getAccountId(), authentication);
        assertAccountMatchesLoanCustomer(sourceAccount, loan);
        Account account = accountService.debit(request.getAccountId(), request.getAmount());

        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setLoan(loan);
        payment.setAccount(account);
        // paymentDate y status usan los defaults de la entidad (now() / "COMPLETED")

        Payment saved = paymentRepository.save(payment);

        updateLoanStatusIfFullyPaid(loan);

        return paymentMapper.toResponse(saved);
    }

    // ---------------------------------------------------------------
    // Consultas
    // ---------------------------------------------------------------

    @Transactional(readOnly = true)
    public Page<PaymentResponse> list(UUID loanId, Pageable pageable, Authentication authentication) {
        Page<Payment> page;

        if (loanId != null) {
            Loan loan = findLoanOrThrow(loanId);
            checkLoanOwnership(loan, authentication);
            page = paymentRepository.findByLoanId(loanId, pageable);
        } else if (isCustomer(authentication)) {
            UUID customerId = resolveAuthenticatedCustomerId(authentication);
            page = paymentRepository.findByLoan_Customer_Id(customerId, pageable);
        } else {
            page = paymentRepository.findAll(pageable);
        }

        return page.map(paymentMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getById(UUID id, Authentication authentication) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found."));

        if (payment.getLoan() != null) {
            checkLoanOwnership(payment.getLoan(), authentication);
        }
        if (payment.getAccount() != null) {
            accountService.getAccountById(payment.getAccount().getId(), authentication);
        }

        return paymentMapper.toResponse(payment);
    }

    // ---------------------------------------------------------------
    // Helpers privados
    // ---------------------------------------------------------------

    private Loan findLoanOrThrow(UUID loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found."));
    }

    private void assertApproved(Loan loan) {
        if (loan.getStatus() != LoanStatus.APPROVED) {
            throw new ResponseStatusException(HttpStatus.valueOf(422),
                    "Payments can only be registered for loans in APPROVED status.");
        }
    }

    private void assertAccountMatchesLoanCustomer(AccountResponse account, Loan loan) {
        if (!account.customerId().equals(loan.getCustomer().getId())) {
            throw new ResponseStatusException(HttpStatus.valueOf(422),
                    "The payment account must belong to the loan customer.");
        }
    }

    /**
     * ADR-010: cuenta pagos existentes; si llega a termMonths, cierra el
     * préstamo como PAID.
     */
    private void updateLoanStatusIfFullyPaid(Loan loan) {
        long paymentsCount = paymentRepository.countByLoanId(loan.getId());

        if (paymentsCount >= loan.getTermMonths()) {
            loan.setStatus(LoanStatus.PAID);
            loanRepository.save(loan);
        }
    }

    private UUID resolveAuthenticatedCustomerId(Authentication authentication) {
        UUID currentUserId = ((AppUserPrincipal) authentication.getPrincipal()).getId();
        return customerRepository.findByUser_Id(currentUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "The authenticated user has no associated customer."))
                .getId();
    }

    private void checkLoanOwnership(Loan loan, Authentication authentication) {
        if (!isCustomer(authentication)) {
            return;
        }

        UUID currentUserId = ((AppUserPrincipal) authentication.getPrincipal()).getId();
        boolean owns = customerRepository.findByUser_Id(currentUserId)
                .map(c -> c.getId().equals(loan.getCustomer().getId()))
                .orElse(false);

        if (!owns) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this loan's payments.");
        }
    }

    private boolean isCustomer(Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .anyMatch(role -> "ROLE_CUSTOMER".equals(role));
    }
}
