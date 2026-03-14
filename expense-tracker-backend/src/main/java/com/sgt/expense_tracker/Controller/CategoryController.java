package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.DTO.TransactionType;
import com.sgt.expense_tracker.Model.Category;
import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Service.AuthService;
import com.sgt.expense_tracker.Service.CategoryService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
    CategoryService categoryService ;
    @Autowired
    AuthService authService;

    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping("/create-category")
    public  ResponseEntity<Map<String, String>> createCategory(@RequestBody Category category, org.springframework.security.core.Authentication auth ){
        System.out.println(auth.getName());
        try{
            User user = authService.findUserByEmail(auth.getName());
            int id = user.getUserId();
            categoryService.createCategory(category,id);
        } catch (Exception e) {
            logger.info("Exception me ghusa of createCategory");
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("body","Category Created"));
    }

    @GetMapping("/all-category")
    public List<Category> getCategoryOfUser(org.springframework.security.core.Authentication auth){
        try{
            User user = authService.findUserByEmail(auth.getName());
            int id = user.getUserId();
            return categoryService.getAllCategoriesByUser(id);
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

    @PutMapping("/update-category/{id}")
    public ResponseEntity<Map<String ,String>> updateCategory(
            @PathVariable(name = "id") int categoryId,
            @RequestBody Category category ,org.springframework.security.core.Authentication auth){

        try{
            int id = authService.findUserByEmail(auth.getName()).getUserId();
            System.out.println(category.toString());
            categoryService.updateCategory(
                 categoryId,category,id
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("body", e.getMessage()));
        }

        return ResponseEntity.ok()
                .body(Map.of("body", "Category Updated Successfully"));
    }

    @PutMapping("/delete-category/{id}")
    public ResponseEntity<Map<String,String>> deleteCategory(@PathVariable(name = "id") int categoryId,org.springframework.security.core.Authentication auth){
        try{
            int id = authService.findUserByEmail(auth.getName()).getUserId();
            categoryService.deleteCategoryOfUser(categoryId,id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("body","Deleted Successfully "));
    }


}
