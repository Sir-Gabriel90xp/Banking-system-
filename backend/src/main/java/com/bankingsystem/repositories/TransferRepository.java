package com.bankingsystem.repositories;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingsystem.entities.Transfer;

/**
 * Supuesto a confirmar contra la entidad real: se asume que Transfer tiene
 * los campos "originAccount" y "destinationAccount" (@ManyToOne hacia
 * Account), siguiendo el nombre de columna origin_account_id /
 * destination_account_id usado en V1__init_schema.sql (bitacora.md,
 * Sesión 4). 08_entities.md los documenta como "sourceAccount" /
 * "destinationAccount" — si tu entidad real usa esos nombres, ajustar
 * la consulta JPQL de abajo.
 */
public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    @Query("""
            select t from Transfer t
            where (:accountId is null or t.originAccount.id = :accountId or t.destinationAccount.id = :accountId)
              and (:customerId is null
                   or t.originAccount.customer.id = :customerId
                   or t.destinationAccount.customer.id = :customerId)
              and (:status is null or t.status = :status)
            """)
    Page<Transfer> findHistory(
            @Param("accountId") UUID accountId,
            @Param("customerId") UUID customerId,
            @Param("status") String status,
            Pageable pageable);

    long countByOriginAccountIdAndCreatedAtAfter(UUID originAccountId, Instant since);
} 
