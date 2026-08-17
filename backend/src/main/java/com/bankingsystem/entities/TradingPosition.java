package com.bankingsystem.entities;

import java.math.BigDecimal;

import com.bankingsystem.entities.base.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trading_position")
@Getter
@Setter
@NoArgsConstructor
public class TradingPosition extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private TradingWallet wallet;

    @Column(name = "symbol", nullable = false, length = 30)
    private String symbol;

    @Column(name = "display_symbol", nullable = false, length = 40)
    private String displaySymbol;

    @Column(name = "quantity", nullable = false, precision = 19, scale = 6)
    private BigDecimal quantity = BigDecimal.ZERO;

    @Column(name = "average_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal averagePrice = BigDecimal.ZERO;
}
