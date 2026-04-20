CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    
    role VARCHAR(10) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL ,
    password VARCHAR(255) NOT NULL,
    
    cpf CHAR(11) UNIQUE NOT NULL ,
    name VARCHAR(255) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,
    last_login TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT check_users_role CHECK (role IN ('ADMIN', 'USER')),
    CONSTRAINT check_email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    CONSTRAINT check_cpf_format CHECK (cpf ~ '^[0-9]{11}$')
);