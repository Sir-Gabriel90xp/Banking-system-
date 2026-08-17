CREATE UNIQUE INDEX ux_account_customer_type_not_deleted
    ON account (customer_id, account_type)
    WHERE deleted = false;
