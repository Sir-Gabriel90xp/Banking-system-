-- =====================================================================
-- V5__add_trading.sql
-- Trading: wallet de inversion, posiciones y ordenes ejecutadas
-- =====================================================================

CREATE TABLE trading_wallet (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id  UUID NOT NULL UNIQUE REFERENCES customer(id),
    cash_balance NUMERIC(19,2) NOT NULL DEFAULT 0 CHECK (cash_balance >= 0),
    currency     VARCHAR(3) NOT NULL DEFAULT 'USD',

    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by   VARCHAR(100),
    updated_by   VARCHAR(100)
);

CREATE INDEX idx_trading_wallet_customer_id ON trading_wallet(customer_id);

CREATE TABLE trading_position (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    wallet_id      UUID NOT NULL REFERENCES trading_wallet(id),
    symbol         VARCHAR(30) NOT NULL,
    display_symbol VARCHAR(40) NOT NULL,
    quantity       NUMERIC(19,6) NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    average_price  NUMERIC(19,4) NOT NULL DEFAULT 0 CHECK (average_price >= 0),

    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by     VARCHAR(100),
    updated_by     VARCHAR(100),

    CONSTRAINT uk_trading_position_wallet_symbol UNIQUE (wallet_id, symbol)
);

CREATE INDEX idx_trading_position_wallet_id ON trading_position(wallet_id);

CREATE TABLE trading_order (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    wallet_id      UUID NOT NULL REFERENCES trading_wallet(id),
    account_id     UUID REFERENCES account(id),
    symbol         VARCHAR(30) NOT NULL,
    display_symbol VARCHAR(40) NOT NULL,
    side           VARCHAR(10) NOT NULL,
    quantity       NUMERIC(19,6) NOT NULL CHECK (quantity > 0),
    price          NUMERIC(19,4) NOT NULL CHECK (price > 0),
    gross_amount   NUMERIC(19,2) NOT NULL CHECK (gross_amount > 0),
    status         VARCHAR(20) NOT NULL DEFAULT 'FILLED',

    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by     VARCHAR(100),
    updated_by     VARCHAR(100)
);

CREATE INDEX idx_trading_order_wallet_id ON trading_order(wallet_id);
CREATE INDEX idx_trading_order_symbol ON trading_order(symbol);
