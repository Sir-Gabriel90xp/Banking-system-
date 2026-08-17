ALTER TABLE payment
    ADD COLUMN account_id UUID REFERENCES account(id);

CREATE INDEX idx_payment_account_id ON payment(account_id);
