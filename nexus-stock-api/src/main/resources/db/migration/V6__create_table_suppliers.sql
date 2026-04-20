CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(255) UNIQUE NOT NULL,
    cnpj CHAR(14) UNIQUE NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT check_cnpj_format CHECK (cnpj ~ '^[0-9]{14}$')
);