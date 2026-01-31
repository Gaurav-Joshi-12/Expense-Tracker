package com.sgt.expense_tracker.Model;


//CREATE TABLE CATEGORIES(
//        category_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
//        user_id INT NOT NULL,
//        category_name VARCHAR(50),
//description VARCHAR(200),
//icon_url VARCHAR(255),
//transcation_type ENUM('INCOME','EXPENSE')NOT NULL DEFAULT 'EXPENSE',
//active_yn INT DEFAULT 1 ,
//created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE CASCADE
//
//);

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    int categoryId;
    int userId;
    String categoryName;
    String description;
    String iconUrl;
    String transactionType;
    int activeYn;
}
