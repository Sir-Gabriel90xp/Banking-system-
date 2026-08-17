package com.bankingsystem.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingsystem.entities.TradingPosition;

public interface TradingPositionRepository extends JpaRepository<TradingPosition, UUID> {

    List<TradingPosition> findByWalletIdOrderBySymbolAsc(UUID walletId);

    Optional<TradingPosition> findByWalletIdAndSymbol(UUID walletId, String symbol);
}
