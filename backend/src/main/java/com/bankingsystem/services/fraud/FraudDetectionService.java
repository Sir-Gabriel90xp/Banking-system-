package com.bankingsystem.services.fraud;

import java.time.Instant;
import java.util.List; // ASUNCIÓN: com.bankingsystem.entities.Account
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ASUNCIÓN: forma del evento creado en la Ronda 2 de Auditoría

import com.bankingsystem.config.fraud.FraudRulesProperties; // ASUNCIÓN: nombre de la clase creada en la Ronda 2
import com.bankingsystem.entities.Account;
import com.bankingsystem.entities.FraudAlert; // ASUNCIÓN: ya existe (creado junto con AccountService)
import com.bankingsystem.entities.enums.FraudSeverity;
import com.bankingsystem.events.audit.AuditEvent; // ASUNCIÓN: expone un método de bloqueo (ver comentario abajo)
import com.bankingsystem.events.audit.AuditEventPublisher;
import com.bankingsystem.events.fraud.FraudCheckEvent;
import com.bankingsystem.repositories.AccountRepository;
import com.bankingsystem.repositories.FraudAlertRepository;
import com.bankingsystem.services.AccountService;

/**
 * Orquesta la detección de fraude (RF-08.1 a RF-08.3, CU-11).
 * Se invoca de forma asíncrona desde FraudCheckListener, un mensaje de
 * RabbitMQ por vez, nunca desde el hilo de la petición HTTP original.
 */
@Service
public class FraudDetectionService {

    private final List<FraudRule> rules;
    private final FraudRulesProperties properties;
    private final FraudAlertRepository fraudAlertRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AuditEventPublisher auditEventPublisher;

    public FraudDetectionService(List<FraudRule> rules,
                                  FraudRulesProperties properties,
                                  FraudAlertRepository fraudAlertRepository,
                                  AccountRepository accountRepository,
                                  AccountService accountService,
                                  AuditEventPublisher auditEventPublisher) {
        this.rules = rules;
        this.properties = properties;
        this.fraudAlertRepository = fraudAlertRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public void evaluate(FraudCheckEvent event) {
        List<FraudSignal> signals = rules.stream()
                .map(rule -> rule.evaluate(event))
                .flatMap(Optional::stream)
                .toList();

        if (signals.isEmpty()) {
            return;
        }

        // ASUNCIÓN: AccountRepository.findById(UUID) devuelve Optional<Account>.
        Account account = accountRepository.findById(event.getOriginAccountId())
                .orElseThrow(() -> new IllegalStateException(
                        "Cuenta no encontrada al evaluar fraude: " + event.getOriginAccountId()));

        FraudSeverity highestSeverity = signals.stream()
                .map(FraudSignal::severity)
                .max(FraudSeverity::compareTo)
                .orElseThrow();

        for (FraudSignal signal : signals) {
            FraudAlert alert = new FraudAlert(account, signal.reason(), signal.severity(), Instant.now());
            fraudAlertRepository.save(alert);
        }

        // RF-08.3: bloqueo automático si la severidad más alta detectada
        // alcanza el umbral configurado (fraud.auto-block-severity).
        if (highestSeverity.compareTo(properties.getAutoBlockSeverity()) >= 0) {
            accountService.blockAccount(account.getId());
        }

        // CU-11 paso 4: registrar auditoría del evento de fraude.
        auditEventPublisher.publish(AuditEvent.of(
                null,
                "FRAUD_DETECTED",
                "Account",
                account.getId(),
                null));
    }
}
