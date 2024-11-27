CREATE TABLE IF NOT EXISTS wallets
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT         NOT NULL,
    balance     NUMERIC(10, 2) NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
