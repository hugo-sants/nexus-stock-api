CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    street VARCHAR(255) NOT NULL,
    number VARCHAR(20),
    complement VARCHAR(100),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,

    cep VARCHAR(20) NOT NULL,
    is_main BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT check_cep_format CHECK (cep ~ '^[0-9]{8}$')
);