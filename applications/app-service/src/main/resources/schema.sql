    DROP TABLE IF EXISTS users;
    DROP TABLE IF EXISTS roles;
    CREATE TABLE roles (
            id SERIAL PRIMARY KEY,
            name VARCHAR(50) UNIQUE NOT NULL,
            description VARCHAR(255)
        );
    CREATE TABLE users (
        id SERIAL PRIMARY KEY,
        first_name VARCHAR(100) NOT NULL,
        last_name VARCHAR(100) NOT NULL,
        identity_document VARCHAR(50) UNIQUE NOT NULL,
        birth_date DATE,
        address VARCHAR(255),
        phone VARCHAR(50),
        email VARCHAR(100) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        base_salary DECIMAL(15, 2) NOT NULL,
        role_id BIGINT NOT NULL,
        CONSTRAINT fk_role FOREIGN KEY(role_id) REFERENCES roles(id)
    );
    INSERT INTO roles (id, name, description) VALUES (1, 'ADMIN', 'Administrador del sistema');
    INSERT INTO roles (id, name, description) VALUES (2, 'ASESOR', 'Asesor de créditos');
    INSERT INTO roles (id, name, description) VALUES (3, 'CLIENTE', 'Cliente solicitante de crédito');

    INSERT INTO users (first_name, last_name, email, identity_document, phone, role_id, base_salary, password)
    VALUES ('Admin', 'CrediYa', 'admin@crediya.com', '11111111', '999888777', 1, 10000.00, '$2a$12$4CSqziAIHnhB4eCWHyI/AuZkdn5gDRr0/.y0Rew8cZ7w/UrFfg.eO');
    INSERT INTO users (first_name, last_name, email, identity_document, phone, role_id, base_salary, password)
    VALUES ('Carla', 'Soto', 'cliente@crediya.com', '22222222', '911222333', 3, 50000.00, '$2a$12$4CSqziAIHnhB4eCWHyI/AuZkdn5gDRr0/.y0Rew8cZ7w/UrFfg.eO');
