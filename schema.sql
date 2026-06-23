-- Banking Transaction System Database Schema

CREATE DATABASE IF NOT EXISTS banking_db;
USE banking_db;

-- Accounts table
CREATE TABLE IF NOT EXISTS accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    account_holder VARCHAR(100) NOT NULL,
    account_type ENUM('SAVINGS', 'CURRENT') NOT NULL,
    balance DOUBLE DEFAULT 0.0,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Transactions table
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    type ENUM('DEPOSIT', 'WITHDRAW', 'TRANSFER') NOT NULL,
    amount DOUBLE NOT NULL,
    status ENUM('SUCCESS', 'FAILED') DEFAULT 'SUCCESS',
    description VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

-- Sample data
INSERT INTO accounts (account_holder, account_type, balance, email) VALUES
('Harini S', 'SAVINGS', 50000.00, 'harini@example.com'),
('Priya R', 'CURRENT', 100000.00, 'priya@example.com'),
('Arun K', 'SAVINGS', 25000.00, 'arun@example.com');
