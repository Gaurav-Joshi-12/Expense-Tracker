package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.Model.Category;
import com.sgt.expense_tracker.Model.Transaction;
import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Service.AiService;
import com.sgt.expense_tracker.Service.AuthService;
import com.sgt.expense_tracker.Service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/api/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    AuthService authService;

    @Autowired
    AiService aiService;

    private static Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @PostMapping
    public ResponseEntity<Map<String, String>> createCategory(@RequestBody Transaction transaction, org.springframework.security.core.Authentication auth ){
        System.out.println(auth.getName());
        try{
            User user = authService.findUserByEmail(auth.getName());
            int id = user.getUserId();
            transactionService.createTransaction(transaction,id);
        } catch (Exception e) {
            logger.info("Exception me ghusa of transaction");
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("body","Transaction Created"));
    }

    @GetMapping
   public ResponseEntity<?> getTransaction(@RequestParam(name = "category" , required= false)String category, @RequestParam(name = "start" , required= false) LocalDate start, @RequestParam(name = "end" , required= false) LocalDate end,@RequestParam(name = "type" , required= false)String type,@RequestParam(name = "sortColumn" , required= false)String sortColumn,@RequestParam(name = "sortDir" , required= false , defaultValue = "DESC") String sortDir ,@RequestParam(name = "rowsPerPage" , required= false , defaultValue = "7") Integer rowsPerPage,@RequestParam(name = "pageNo" , required= false , defaultValue = "1") Integer pageNo, org.springframework.security.core.Authentication auth){
       try{
           User user = authService.findUserByEmail(auth.getName());
           int id = user.getUserId();
           return ResponseEntity.ok().body( transactionService.getTransaction(id,category,start,end,type,sortColumn,sortDir,rowsPerPage,pageNo)) ;
       }
       catch (Exception e) {
           logger.info("Exception me ghusa of transaction");
           return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
       }

    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<List<List<String>>> bulkUpload(@RequestParam(name="file")MultipartFile file, org.springframework.security.core.Authentication auth) throws IOException {
        System.out.println(file.getOriginalFilename());
        int id = authService.findUserByEmail(auth.getName()).getUserId();
        List<List<String>> result = transactionService.read(file,id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/suggest-category/{note}")
    public String suggestCategory(@PathVariable(name = "note") String note){
        return aiService.suggestCategory(note,List.of("food,clothing,entertainment"));

    }

}
