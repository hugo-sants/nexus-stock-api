CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,

    cost_price NUMERIC(10,2) NOT NULL,
    sale_price NUMERIC(10,2) NOT NULL,
    min_stock INT NOT NULL DEFAULT 0,
    quantity INT NOT NULL DEFAULT 0,

    category_id BIGINT,
    supplier_id BIGINT,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT check_quantity CHECK (quantity >= 0),
    CONSTRAINT check_cost_positive CHECK (cost_price >= 0),
    CONSTRAINT check_sale_positive CHECK (sale_price >= 0),
    CONSTRAINT check_price CHECK (sale_price >= cost_price)
);