create table users (
    id                      bigserial PRIMARY KEY,
    username                VARCHAR(30) NOT NULL UNIQUE,
    password                VARCHAR(80) NOT NULL,
    email                   VARCHAR(80) UNIQUE,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table roles (
    id                      bigserial PRIMARY KEY,
    name                    VARCHAR(50) NOT NULL UNIQUE,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users_roles (
    user_id                 bigint NOT NULL REFERENCES users (id),
    role_id                 bigint NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

insert into users (username, password, email)
values
('user', '$2y$12$4g1SOm4vGFSF/CbT84nOzOyygKSuTtRshecj7HYOCC1xUPjhkVPWG', 'bob_johnson@gmail.com'),
('admin', '$2y$12$4g1SOm4vGFSF/CbT84nOzOyygKSuTtRshecj7HYOCC1xUPjhkVPWG', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);

CREATE TABLE categories (
    id                      bigserial PRIMARY KEY,
    title                   VARCHAR(255),
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO categories (title)
VALUES
('Clothes'),
('Shoes');

CREATE TABLE products (
    id                      bigserial PRIMARY KEY,
    title                   VARCHAR(255),
    price                   NUMERIC(8, 2),
    category_id             bigint REFERENCES categories(id),
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO products (title, price, category_id)
VALUES
('Jeans', 5000.80, 1),
('Singlet', 1000, 1),
('Jacket', 9000.45, 1),
('Snickers', 4000.30, 2),
('Boots', 3500.99, 2);

CREATE TABLE orders (
    id                      bigserial PRIMARY KEY,
    title                   VARCHAR(255),
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
    id                      bigserial PRIMARY KEY,
    product_id              bigint REFERENCES products(id),
    order_id                bigint REFERENCES orders(id),
    quantity                INT,
    price_per_product       NUMERIC (8, 2),
    total_price             NUMERIC (8, 2),
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);