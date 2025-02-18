CREATE TABLE IF NOT EXISTS bank_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    phone_number VARCHAR(20),
    email VARCHAR(255),
    bank_account_number VARCHAR(50),
    bank_name VARCHAR(255),
    money BIGINT
);

INSERT INTO bank_account (name, phone_number, email, bank_account_number, bank_name, money)
VALUES ('Vegeta', '0123456789', 'vegeta@example.com', '1234567890', 'Bank A', 10000000),
       ('Son Goku', '987654321', 'sonGoku@example.com', '1234567890', 'Bank A', 20000000);
