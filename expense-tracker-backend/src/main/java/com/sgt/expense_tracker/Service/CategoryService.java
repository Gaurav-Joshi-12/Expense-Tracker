package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Exceptions.Cateogory.CategoryDoesNotExist;
import com.sgt.expense_tracker.Exceptions.Cateogory.CategoryExists;
import com.sgt.expense_tracker.Exceptions.Cateogory.InvalidCategory;
import com.sgt.expense_tracker.Model.Category;
import com.sgt.expense_tracker.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository = new CategoryRepository();

    public void createCategory(Category category , int userId){
//        int categoryId = category.getCategoryId();
//        int userId = id;
        String categoryName = category.getCategoryName();
        String description = category.getDescription();
        String iconUrl = category.getIconUrl();
        String transactionType = category.getTransactionType();

        Integer val = categoryRepository.doesCategoryExists(userId,categoryName,transactionType);
        if(val!=null)   throw new CategoryExists("Category already exists");

        categoryRepository.createCategory(userId,categoryName,description,iconUrl,transactionType);
    }


    public void updateCategoryTransactionType(int categoryId ,String transactionType ){
        Integer count = categoryRepository.findCategoryById(categoryId);
        if(count == 0)    throw  new InvalidCategory("Category Not present to Update");
        categoryRepository.updateCategoryTransactionType(categoryId,transactionType);
    }

    public List<Category>  getAllCategoriesByUser(int userId){
        return categoryRepository.getAllCategoriesOfUser(userId);
    }

//    public void deleteCategoryOfUser(int categoryId , int userId){
//        Integer id = categoryRepository.findUserByCategoryId(categoryId);
//        if(id != userId)  throw new InvalidCategory("Category Not present to delete");
//        categoryRepository.deleteCategoryOfUser(categoryId,userId);
//
//    }

    public void deleteCategoryOfUser(int categoryId ){
//        Integer id = categoryRepository.findUserByCategoryId(categoryId);
//        if(id != userId)  throw new InvalidCategory("Category Not present to delete");
        Integer id = categoryRepository.findCategoryById(categoryId);
        if(id == null) throw new CategoryDoesNotExist("Category Does Not exist");
        categoryRepository.deleteCategoryOfUser(categoryId);

    }

}
