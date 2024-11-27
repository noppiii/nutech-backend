DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_type WHERE typname = 'payment_method_type') THEN
DROP TYPE payment_method_type;
END IF;
END $$;

CREATE TYPE payment_method_type AS ENUM (
    'E_WALLET',
    'BANK_TRANSFER',
    'CREDIT_CARD',
    'DANA',
    'SHOPEEPAY',
    'BNI',
    'BRI',
    'MANDIRI'
);

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_type WHERE typname = 'transaction_type') THEN
DROP TYPE transaction_type;
END IF;
END $$;

CREATE TYPE transaction_type AS ENUM (
    'TOP_UP',
    'PURCHASE'
);

CREATE TABLE IF NOT EXISTS transactions
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT         NOT NULL,
    invoice_number VARCHAR(255)   NOT NULL,
    name           VARCHAR(255)   NOT NULL,
    description    TEXT,
    amount         NUMERIC(10, 2) NOT NULL,
    type           transaction_type NOT NULL,
    payment_method payment_method_type NOT NULL,
    created_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at     TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
    );
