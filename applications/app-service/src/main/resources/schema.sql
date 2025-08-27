    DROP TABLE IF EXISTS users;

    CREATE TABLE users (
        id BIGSERIAL PRIMARY KEY,
        first_name VARCHAR(255) NOT NULL,
        last_name VARCHAR(255) NOT NULL,
        birth_date DATE,
        address VARCHAR(255),
        phone VARCHAR(50),
        email VARCHAR(255) UNIQUE NOT NULL,
        base_salary NUMERIC(12, 2) NOT NULL
    );