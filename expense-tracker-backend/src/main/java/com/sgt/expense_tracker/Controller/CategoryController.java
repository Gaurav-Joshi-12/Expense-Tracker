package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.DTO.TransactionType;
import com.sgt.expense_tracker.Model.Category;
import com.sgt.expense_tracker.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService = new CategoryService();

    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping("/create-category/{id}")
    public  ResponseEntity<Map<String, String>> createCategory(@RequestBody Category category ,@PathVariable(name = "id") int userId ){
        try{
            categoryService.createCategory(category,userId);
        } catch (Exception e) {
            logger.info("Exception me ghusa of createCategory");
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("body","Category Created"));
    }

    @GetMapping("/all-category/{id}")
    public List<Category> getCategoryOfUser(@PathVariable(name = "id") int userId){
        try{
            return categoryService.getAllCategoriesByUser(userId);
        } catch (Exception e) {
            logger.info("Exception me ghusa of getCategoryOfUser");
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/update-transaction-type/{id}")
    public ResponseEntity<Map<String ,String>> updateTransactionType(@PathVariable(name = "id") int categoryId,
                                                                     @RequestBody TransactionType request ){
        try{
            categoryService.updateCategoryTransactionType(categoryId , request.getTransactionType());

        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("body","Updated Transaction to "+ request.getTransactionType()));
    }

    @PutMapping("/delete-category/{id}")
    public ResponseEntity<Map<String,String>> deleteCategory(@PathVariable(name = "id") int categoryId){
        try{
            categoryService.deleteCategoryOfUser(categoryId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("body","Deleted Successfully "));
    }


}
