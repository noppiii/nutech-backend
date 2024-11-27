CREATE TABLE IF NOT EXISTS purchases
(
    id             BIGSERIAL PRIMARY KEY,
    transaction_id BIGINT    NOT NULL,
    product_id     BIGINT    NOT NULL,
    quantity       INT       NOT NULL,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at     TIMESTAMP,
    FOREIGN KEY (transaction_id) REFERENCES transactions (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);
