CREATE TABLE IF NOT EXISTS `account` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    phone_number VARCHAR(20),
    email VARCHAR(255),
    bank_account_number VARCHAR(50),
    bank_name VARCHAR(255),
    money BIGINT
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    role VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(512),
    phone_number VARCHAR(20),
    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS device (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255),
    name VARCHAR(255),
    quantity INTEGER,
    price BIGINT,
    user_id BIGINT
);

INSERT INTO `account` (name, phone_number, email, bank_account_number, bank_name, money)
VALUES ('Vegeta', '0123456789', 'vegeta@example.com', '1234567890', 'Bank A', 10000000),
       ('Son Goku', '987654321', 'sonGoku@example.com', '987654321', 'Bank A', 20000000);

INSERT INTO users (name, role, phone_number, username, password, email)
VALUES ('Vegeta', 'admin', '0123456789', 'vegeta', '$2a$12$YNvwSFjBJlC8SG/.MFJYoeuQt/QglY67D9iPb8VSDeilbvJZobphm', 'vegeta@example.com'),
       ('Son Goku', 'client', '987654321', 'songoku', '$2a$12$YNvwSFjBJlC8SG/.MFJYoeuQt/QglY67D9iPb8VSDeilbvJZobphm', 'songoku@example.com'),
       ('Son GoHan', 'client', '012345678', 'songohan', '$2a$12$YNvwSFjBJlC8SG/.MFJYoeuQt/QglY67D9iPb8VSDeilbvJZobphm', 'songohan@example.com'),
       ('Son GoTen', 'client', '01234567', 'songoten', '$2a$12$YNvwSFjBJlC8SG/.MFJYoeuQt/QglY67D9iPb8VSDeilbvJZobphm', 'songoten@example.com');

INSERT INTO device  (type, name, quantity, price, user_id)
VALUES ('phone', 'Iphone 15', 2, 30000000, 1),
       ('phone', 'Samsung S22', 1, 35000000, 1),
       ('laptop', 'Macbook Pro 2024', 2, 60000000, 1),
       ('tablet', 'Ipad 2024', 1, 40000000, 1),
       ('phone', 'Iphone 16', 1, 45000000, 2),
       ('phone', 'Samsung Z Fold', 5, 50000000, 2),
       ('laptop', 'Predator Pro', 2, 38000000, 2),
       ('tablet', 'Ipad 2023', 4, 20000000, 2),
       ('PC', 'PC Gaming', 1, 15000000, 2),
       ('phone', 'Iphone 11 Pro Max', 1, 45000000, 3),
       ('phone', 'Samsung Z Flip', 5, 50000000, 3),
       ('laptop', 'Nitro 5', 2, 38000000, 4),
       ('tablet', 'Ipad 2025', 4, 20000000, 4),
       ('PC', 'PC Office', 1, 15000000, 4);