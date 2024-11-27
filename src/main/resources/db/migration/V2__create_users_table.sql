DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_type WHERE typname = 'role_type') THEN
DROP TYPE role_type;
END IF;
END $$;

CREATE TYPE role_type AS ENUM ('USER', 'ADMIN');

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_type WHERE typname = 'social_code_type') THEN
DROP TYPE social_code_type;
END IF;
END $$;

CREATE TYPE social_code_type AS ENUM ('NORMAL');

CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    name        VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    social_code social_code_type NOT NULL DEFAULT 'NORMAL',
    role        role_type NOT NULL DEFAULT 'USER',
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP
    );
