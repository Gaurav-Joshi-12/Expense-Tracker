package com.sgt.expense_tracker.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


//
//CREATE TABLE TRANSACTIONS(
//        transaction_id INT PRIMARY KEY NOT NULL  AUTO_INCREMENT,
//        user_id INT NOT NULL,
//        category_id INT NOT NULL,
//        amount DECIMAL(10,3),
//date_of_transaction DATE,
//notes VARCHAR(200),
//
//active_yn INT DEFAULT 1 ,
//created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//
//FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE CASCADE,
//FOREIGN KEY (category_id) REFERENCES CATEGORIES(category_id) ON DELETE CASCADE
//
//);

@Getter
@Setter
public class Transaction {
    int transactionId;
    int userId;
    int categoryId;
    double amount;
    LocalDate dateOfTransaction;
    String notes;
    int activeYn;
    String categoryName;
    String categoryType;
}
