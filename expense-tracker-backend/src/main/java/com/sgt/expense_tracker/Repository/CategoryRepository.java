package com.sgt.expense_tracker.Repository;


import com.sgt.expense_tracker.Mapper.CategoryMapper;
import com.sgt.expense_tracker.Model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
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

@Repository
public class CategoryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer doesCategoryExists(int userId , String categoryName , String transactionType){
        String query = "SELECT category_id FROM CATEGORIES WHERE USER_ID = ? AND CATEGORY_NAME = ? AND TRANSACTION_TYPE = ?\n";
        try{
            return jdbcTemplate.queryForObject(query,Integer.class,userId,categoryName,transactionType);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }


    public void createCategory(int userId,String categoryName,String description,String iconUrl,String transactionType){
        String query="INSERT INTO categories(user_id,category_name,description,icon_url,transaction_type) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, userId, categoryName, description, iconUrl, transactionType);
    }

    public Integer findCategoryById(int categoryId){
        String query = "SELECT COUNT(*) FROM categories where category_id = ?";
        return jdbcTemplate.queryForObject(query,Integer.class,categoryId);
    }

    public Integer findUserByCategoryId(int categoryId){
        String query = "SELECT user_id FROM categories where category_id = ?";
        return jdbcTemplate.queryForObject(query,Integer.class,categoryId);
    }

    public void updateCategoryTransactionType(int categoryId,String transactionType){
        String sql = "UPDATE categories set transaction_type = ? WHERE category_id = ?";
        jdbcTemplate.update(sql,transactionType,categoryId);
    }

    public List<Category> getAllCategoriesOfUser(int userId){
        String query = "Select * from categories where user_id = ? and active_yn = 1";
        return jdbcTemplate.query(query,new CategoryMapper(),userId);
    }

//    public void deleteCategoryOfUser(int categoryId,int userId){
//        String sql = "UPDATE category set active_yn = ? WHERE category_id = ? and user_id";
//        jdbcTemplate.update(sql,categoryId,userId);
//    }

        public void deleteCategoryOfUser(int categoryId){
        String sql = "UPDATE categories set active_yn = 0 WHERE category_id =?";
        jdbcTemplate.update(sql,categoryId);
    }


}
