package com.bankingsystem.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingsystem.entities.TradingWallet;

public interface TradingWalletRepository extends JpaRepository<TradingWallet, UUID> {

    Optional<TradingWallet> findByCustomerId(UUID customerId);
}
