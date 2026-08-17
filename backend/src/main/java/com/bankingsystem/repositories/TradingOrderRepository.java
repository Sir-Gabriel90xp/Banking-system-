package com.bankingsystem.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingsystem.entities.TradingOrder;

public interface TradingOrderRepository extends JpaRepository<TradingOrder, UUID> {

    Page<TradingOrder> findByWalletId(UUID walletId, Pageable pageable);
}
