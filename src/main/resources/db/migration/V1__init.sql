CREATE TABLE categories (id bigserial PRIMARY KEY, title VARCHAR(255));
INSERT INTO categories (title) VALUES
('Clothes'),
('Shoes');

CREATE TABLE products (id bigserial PRIMARY KEY, title VARCHAR(255), price int, category_id bigint REFERENCES categories(id));
INSERT INTO products (title, price, category_id) VALUES
('Jeans', 5000, 1),
('Singlet', 1000, 1),
('Jacket', 9000, 1),
('Snickers', 4000, 2),
('Boots', 3500, 2);
