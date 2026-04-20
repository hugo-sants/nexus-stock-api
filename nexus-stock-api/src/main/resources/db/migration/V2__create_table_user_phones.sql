CREATE TABLE user_phones (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    phone VARCHAR(15) NOT NULL,
    
    CONSTRAINT check_phone_format CHECK (phone ~ '^[0-9]{10,15}$')
);