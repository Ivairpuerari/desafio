-- init tables in database
CREATE DATABASE IF NOT EXISTS transaction_db;

USE transaction_db;

-- Table for accounts
CREATE TABLE accounts  (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         number VARCHAR(20) NOT NULL UNIQUE,
                         holder_name VARCHAR(255) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table for balance categories
CREATE TABLE balance_categories  (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         account_id INT NOT NULL,
                         category_name VARCHAR(50) NOT NULL,
                         amount DECIMAL(10, 2) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         version BIGINT,
                         FOREIGN KEY (account_id) REFERENCES accounts(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table for transactions
CREATE TABLE transactions  (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             account_id INT,
                             total_amount DECIMAL(15, 2) NOT NULL,
                             mcc VARCHAR(4) NOT NULL,
                             merchant VARCHAR(100) NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (account_id) REFERENCES accounts(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table for merchant to mcc override
CREATE TABLE merchant_to_mcc_overrides (
                                           merchant VARCHAR(255) PRIMARY KEY,
                                           mcc VARCHAR(4) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO accounts (number, holder_name) VALUES
                                               ('1234567890', 'John Doe'),
                                               ('0987654321', 'Jane Smith');

INSERT INTO balance_categories (account_id, category_name, amount, version) VALUES
                                                                       (1, 'FOOD', 150.00, 1),
                                                                       (1, 'CASH', 75.00, 1),
                                                                       (2, 'MEAL', 200.00, 1);

INSERT INTO merchant_to_mcc_overrides (merchant, mcc) VALUES
                                                          ('Coffee Shop', '5412')
