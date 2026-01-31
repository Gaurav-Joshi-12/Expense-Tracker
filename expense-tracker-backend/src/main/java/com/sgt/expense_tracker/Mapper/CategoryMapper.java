package com.sgt.expense_tracker.Mapper;

import com.sgt.expense_tracker.Model.Category;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class CategoryMapper implements RowMapper<Category> {
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException{
        Category category = new Category();
        int categoryId = rs.getInt("category_id");
        int userId = rs.getInt("user_id");
        String categoryName = rs.getString("category_name");
        String description = rs.getString("description");
        String iconUrl = rs.getString("icon_url");
        String transactionType = rs.getString("transaction_type");
        int activeYn = rs.getInt("active_yn");

        category.setCategoryId(categoryId);
        category.setUserId(userId);
        category.setCategoryName(categoryName);
        category.setDescription(description);
        category.setIconUrl(iconUrl);
        category.setTransactionType(transactionType);
        category.setActiveYn(activeYn);

        return category;
    }
}
