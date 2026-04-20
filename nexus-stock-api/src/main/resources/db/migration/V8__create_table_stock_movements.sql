CREATE TABLE stock_movements (
    id BIGSERIAL PRIMARY KEY,

    product_id BIGINT NOT NULL,
    user_id BIGINT,

    stock_type VARCHAR(3) NOT NULL,
    quantity INT NOT NULL,

    reason VARCHAR(255),

    created_at TIMESTAMP DEFAULT NOW(),

    CONSTRAINT check_quantity_positive CHECK (quantity > 0),
    CONSTRAINT check_stock_type CHECK (stock_type IN ('IN','OUT'))
);