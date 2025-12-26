CREATE DATABASE EXPENSE_TRACKER;

USE EXPENSE_TRACKER;


CREATE TABLE USERS(
	 user_id INT PRIMARY KEY NOT NULL  AUTO_INCREMENT,
     name VARCHAR(50) NOT NULL,
	 user_name VARCHAR(50) NOT NULL,
	 password VARCHAR(255) NOT NULL,
     email VARCHAR(100) NOT NULL,
     mobile_no VARCHAR(10),
	 active_yn INT DEFAULT 1 ,
	 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
     );

ALTER TABLE USERS
ADD CONSTRAINT uk_users_email UNIQUE (email);


CREATE TABLE CATEGORIES(
	category_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    category_name VARCHAR(50),
    description VARCHAR(200),
    icon_url VARCHAR(255),
    transcation_type ENUM('INCOME','EXPENSE')NOT NULL DEFAULT 'EXPENSE',
    active_yn INT DEFAULT 1 ,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE CASCADE

);


CREATE TABLE TRANSACTIONS(
	transaction_id INT PRIMARY KEY NOT NULL  AUTO_INCREMENT,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
	amount DECIMAL(10,3),
    date_of_transaction DATE,
    notes VARCHAR(200),

	active_yn INT DEFAULT 1 ,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES CATEGORIES(category_id) ON DELETE CASCADE

);


INSERT INTO USERS (name, user_name, password, email, mobile_no)
VALUES
('Gaurav Lalwani', 'gaurav', 'pass123', 'gaurav@gmail.com', '9876543210'),
('Rushil Ahuja', 'rushil', 'pass123', 'rushil@gmail.com', '9876543211');

INSERT INTO CATEGORIES (user_id, category_name, description, icon_url, transcation_type)
VALUES
-- Gaurav (user_id = 1)
(1, 'Food', 'Daily food expenses', 'https://cdn-icons-png.flaticon.com/512/1046/1046784.png', 'EXPENSE'),
(1, 'Travel', 'Travel & transport', 'https://cdn-icons-png.flaticon.com/512/201/201623.png', 'EXPENSE'),
(1, 'Stocks', 'Stock market income', 'https://cdn-icons-png.flaticon.com/512/2331/2331941.png', 'INCOME'),

-- Rushil (user_id = 2)
(2, 'Electronics', 'Gadgets & devices', 'https://cdn-icons-png.flaticon.com/512/3659/3659899.png', 'EXPENSE'),
(2, 'Food', 'Food & dining', 'https://cdn-icons-png.flaticon.com/512/1046/1046784.png', 'EXPENSE'),
(2, 'Rent', 'Rental income', 'https://cdn-icons-png.flaticon.com/512/1946/1946436.png', 'INCOME');


INSERT INTO TRANSACTIONS (user_id, category_id, amount, date_of_transaction, notes)
VALUES
-- Gaurav transactions (category_id 1,2,3)
(1, 1, 450.000, '2025-01-05', 'Lunch at restaurant'),
(1, 2, 1200.000, '2025-01-06', 'Cab travel'),
(1, 3, 5000.000, '2025-01-07', 'Stock profit'),

-- Rushil transactions (category_id 4,5,6)
(2, 4, 8500.000, '2025-01-05', 'Bluetooth headphones'),
(2, 5, 300.000, '2025-01-06', 'Snacks'),
(2, 6, 9000.000, '2025-01-07', 'House rent received');

select * from users;
select * from transactions;
select * from categories;

