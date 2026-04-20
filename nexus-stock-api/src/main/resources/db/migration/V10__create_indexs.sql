CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_cpf ON users(cpf);
CREATE UNIQUE INDEX unique_main_address_per_user
ON addresses(user_id)
WHERE is_main = TRUE;

CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_supplier_id ON products(supplier_id);

CREATE INDEX idx_stock_product_id ON stock_movements(product_id);
CREATE INDEX idx_stock_user_id ON stock_movements(user_id);

CREATE INDEX idx_addresses_user_id ON addresses(user_id);

CREATE INDEX idx_supplier_addresses_supplier_id ON supplier_addresses(supplier_id);