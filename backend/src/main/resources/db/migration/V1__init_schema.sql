-- =====================================================================
-- V1__init_schema.sql
-- Banking System - Esquema inicial
-- Basado en: docs/database/03_database_model.md (entidades principales)
-- Convenciones: UUID como PK, snake_case, soft delete, timestamps de auditoría
--
-- NOTA DE DISEÑO: la entidad "User" del modelo se implementa aquí como
-- tabla `app_user` en vez de `user`, porque `user` es una palabra
-- reservada en PostgreSQL (choca con la función user). Es una decisión
-- de nomenclatura, no un cambio de modelo; debe registrarse en
-- decisiones.md si se confirma como definitiva.
-- =====================================================================

-- Extensión necesaria para gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =====================================================================
-- ROLE
-- =====================================================================
CREATE TABLE role (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(50) NOT NULL UNIQUE,   -- ADMIN, EMPLOYEE, CUSTOMER
    description VARCHAR(255),

    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by  VARCHAR(100),
    updated_by  VARCHAR(100)
);

-- =====================================================================
-- APP_USER  (Requisito: RF-01, RN-03)
-- =====================================================================
CREATE TABLE app_user (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username    VARCHAR(100) NOT NULL UNIQUE,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    enabled     BOOLEAN NOT NULL DEFAULT true,

    role_id     UUID NOT NULL REFERENCES role(id),

    deleted     BOOLEAN NOT NULL DEFAULT false,
    deleted_at  TIMESTAMPTZ,

    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by  VARCHAR(100),
    updated_by  VARCHAR(100)
);

CREATE INDEX idx_app_user_email    ON app_user(email);
CREATE INDEX idx_app_user_username ON app_user(username);

-- =====================================================================
-- CUSTOMER  (Requisito: RF-02, RN-03)
-- =====================================================================
CREATE TABLE customer (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    document_number VARCHAR(50)  NOT NULL UNIQUE,
    birth_date      DATE,
    phone           VARCHAR(30),
    email           VARCHAR(150) NOT NULL UNIQUE,
    address         VARCHAR(255),
    status          VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, INACTIVE

    -- Un Customer puede (opcionalmente) estar vinculado a una cuenta de acceso
    user_id         UUID REFERENCES app_user(id),

    deleted         BOOLEAN NOT NULL DEFAULT false,
    deleted_at      TIMESTAMPTZ,

    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100)
);

CREATE INDEX idx_customer_document ON customer(document_number);
CREATE INDEX idx_customer_email    ON customer(email);

-- =====================================================================
-- ACCOUNT  (Requisito: RF-03, RN-01)
-- =====================================================================
CREATE TABLE account (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_number VARCHAR(34) NOT NULL UNIQUE,
    account_type   VARCHAR(20) NOT NULL,             -- SAVINGS, CHECKING, etc.
    balance        NUMERIC(19,2) NOT NULL DEFAULT 0 CHECK (balance >= 0),
    currency       VARCHAR(3)  NOT NULL DEFAULT 'DOP',
    status         VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, BLOCKED, CLOSED

    customer_id    UUID NOT NULL REFERENCES customer(id),

    deleted        BOOLEAN NOT NULL DEFAULT false,
    deleted_at     TIMESTAMPTZ,

    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by     VARCHAR(100),
    updated_by     VARCHAR(100)
);

CREATE INDEX idx_account_number      ON account(account_number);
CREATE INDEX idx_account_customer_id ON account(customer_id);

-- =====================================================================
-- TRANSACTION  (Requisito: RF-04, RF-06)
-- =====================================================================
CREATE TABLE transaction (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type        VARCHAR(20) NOT NULL,   -- DEPOSIT, WITHDRAWAL, TRANSFER, LOAN_PAYMENT
    amount      NUMERIC(19,2) NOT NULL CHECK (amount > 0),
    description VARCHAR(255),
    date        TIMESTAMPTZ NOT NULL DEFAULT now(),
    status      VARCHAR(20) NOT NULL DEFAULT 'COMPLETED',

    account_id  UUID NOT NULL REFERENCES account(id),

    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by  VARCHAR(100),
    updated_by  VARCHAR(100)
);

CREATE INDEX idx_transaction_account_id ON transaction(account_id);
CREATE INDEX idx_transaction_created_at ON transaction(created_at);

-- =====================================================================
-- TRANSFER  (Requisito: RF-04, RN-01, RN-02)
-- =====================================================================
CREATE TABLE transfer (
    id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    origin_account_id      UUID NOT NULL REFERENCES account(id),
    destination_account_id UUID NOT NULL REFERENCES account(id),
    amount                NUMERIC(19,2) NOT NULL CHECK (amount > 0),
    status                VARCHAR(20) NOT NULL DEFAULT 'COMPLETED',
    transfer_date         TIMESTAMPTZ NOT NULL DEFAULT now(),

    created_at            TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at            TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by            VARCHAR(100),
    updated_by            VARCHAR(100),

    CONSTRAINT chk_transfer_different_accounts
        CHECK (origin_account_id <> destination_account_id)  -- RN-02
);

CREATE INDEX idx_transfer_origin      ON transfer(origin_account_id);
CREATE INDEX idx_transfer_destination ON transfer(destination_account_id);

-- =====================================================================
-- LOAN  (Requisito: RF-05, RN-04)
-- =====================================================================
CREATE TABLE loan (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount          NUMERIC(19,2) NOT NULL CHECK (amount > 0),
    interest_rate   NUMERIC(5,2)  NOT NULL,
    term_months     INTEGER NOT NULL CHECK (term_months > 0),
    monthly_payment NUMERIC(19,2) NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, APPROVED, REJECTED, PAID

    customer_id     UUID NOT NULL REFERENCES customer(id), -- RN-04

    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100)
);

CREATE INDEX idx_loan_customer_id ON loan(customer_id);
CREATE INDEX idx_loan_status      ON loan(status);

-- =====================================================================
-- PAYMENT  (Requisito: RF-06)
-- =====================================================================
CREATE TABLE payment (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount         NUMERIC(19,2) NOT NULL CHECK (amount > 0),
    payment_date   TIMESTAMPTZ NOT NULL DEFAULT now(),
    payment_method VARCHAR(30) NOT NULL,
    status         VARCHAR(20) NOT NULL DEFAULT 'COMPLETED',

    loan_id        UUID REFERENCES loan(id),  -- nulo si es pago de servicios (no de préstamo)

    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by     VARCHAR(100),
    updated_by     VARCHAR(100)
);

CREATE INDEX idx_payment_loan_id ON payment(loan_id);

-- =====================================================================
-- AUDIT_LOG  (Requisito: RF-07, RN-05)
-- =====================================================================
CREATE TABLE audit_log (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID REFERENCES app_user(id),  -- responsable (RN-05); puede ser NULL si es 100% del sistema
    action     VARCHAR(100) NOT NULL,
    entity     VARCHAR(100) NOT NULL,
    entity_id  UUID,
    ip_address VARCHAR(45),
    timestamp  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_audit_log_user_id   ON audit_log(user_id);
CREATE INDEX idx_audit_log_timestamp ON audit_log(timestamp);

-- =====================================================================
-- FRAUD_ALERT  (Requisito: RF-08)
-- =====================================================================
CREATE TABLE fraud_alert (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id  UUID NOT NULL REFERENCES account(id),
    reason      VARCHAR(255) NOT NULL,
    severity    VARCHAR(20) NOT NULL, -- LOW, MEDIUM, HIGH, CRITICAL
    detected_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    resolved    BOOLEAN NOT NULL DEFAULT false,

    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_fraud_alert_account_id ON fraud_alert(account_id);

-- =====================================================================
-- SEED: roles base del sistema
-- =====================================================================
INSERT INTO role (name, description) VALUES
    ('ADMIN',    'Gestiona usuarios, roles y supervisa el sistema'),
    ('EMPLOYEE', 'Gestiona clientes, cuentas y aprueba préstamos'),
    ('CUSTOMER', 'Consulta cuentas, realiza transferencias y solicita préstamos');