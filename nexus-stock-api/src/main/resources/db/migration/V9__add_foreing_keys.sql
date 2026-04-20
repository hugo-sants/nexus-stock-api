ALTER TABLE addresses
ADD CONSTRAINT fk_addresses_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE;

ALTER TABLE user_phones
ADD CONSTRAINT fk_user_phones_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE;

ALTER TABLE products
ADD CONSTRAINT fk_products_category
FOREIGN KEY (category_id)
REFERENCES categories(id)
ON DELETE SET NULL;

ALTER TABLE products
ADD CONSTRAINT fk_products_supplier
FOREIGN KEY (supplier_id)
REFERENCES suppliers(id)
ON DELETE SET NULL;

ALTER TABLE supplier_addresses
ADD CONSTRAINT fk_supplier_addresses_supplier
FOREIGN KEY (supplier_id)
REFERENCES suppliers(id)
ON DELETE CASCADE;

ALTER TABLE stock_movements
ADD CONSTRAINT fk_stock_product
FOREIGN KEY (product_id)
REFERENCES products(id)
ON DELETE CASCADE;

ALTER TABLE stock_movements
ADD CONSTRAINT fk_stock_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE SET NULL;

