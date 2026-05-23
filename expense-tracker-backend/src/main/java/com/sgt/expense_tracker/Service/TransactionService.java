package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Model.Category;
import com.sgt.expense_tracker.Model.Transaction;
import com.sgt.expense_tracker.Repository.AuthRepository;
import com.sgt.expense_tracker.Repository.CategoryRepository;
import com.sgt.expense_tracker.Repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.sgt.expense_tracker.Constants.Constants.DEFAULT_CATEGORY_DESCRIPTION;
import static com.sgt.expense_tracker.Constants.Constants.DEFAULT_ICON_URL;
//import static com.sun.beans.introspect.PropertyInfo.Name.description;

@Service
public class TransactionService {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public void createTransaction(Transaction transaction,int userId){
        transactionRepository.createTransaction(userId,transaction.getCategoryId(),transaction.getAmount(),transaction.getDateOfTransaction(),transaction.getNotes());
    }

//    public List<Transaction> getTransaction(int userId){
//        return transactionRepository.getTransaction(userId);
//    }

    public List<Transaction>getTransaction(int id, String category, LocalDate start, LocalDate end, String type,String sortColumn,String sortDir,Integer rowsPerPage,Integer pageNo) {
        return transactionRepository.getTransaction(id,category,start,end,type,sortColumn,sortDir,rowsPerPage,pageNo);
    }

//    public void read(MultipartFile file,int id) throws IOException {
////        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
////        while(br.readLine()!=null){
////            System.out.println(br.readLine());
////        }
//
//        Scanner sc = new Scanner(file.getInputStream());
//        System.out.println(sc.nextLine());
//        while(sc.hasNext()){
//           String row = sc.nextLine();
//           String [] rowData = row.split(",");
////            System.out.print(rowData[0]+"\t");
////            System.out.print(rowData[1]+"\t");
////            System.out.print(rowData[2]+"\t");
////            System.out.print(rowData[3]+"\t");
////            System.out.print(rowData[4]+"\t");
////            System.out.println();
//
////            Double amount = Double.parseDouble(rowData[0]);
//
//            String note = rowData[1];
//            String categoryName = rowData[2];
//            LocalDate date = LocalDate.parse(rowData[3]);
//            String transactionType = rowData[4];
//            Category category = categoryRepository.findCategoryByNameAndTypeAndUserId(categoryName,id,transactionType);
////            logger.info(String.valueOf(category));
//            if(category!=null){
//                logger.info("HAI BABA");
//            }
//            else{
//                logger.info("Category not found creating on 30 feb");
//                categoryRepository.createCategory(id,categoryName,"",DEFAULT_ICON_URL,transactionType);
//                category = categoryRepository.findCategoryByNameAndTypeAndUserId(categoryName,id,transactionType);
//                logger.info("FOund categoru after inserting - {}.{}",category.getCategoryName(),category.getCategoryId());
//            }
//
//
//
//    }
        public List<List<String>> read(MultipartFile file,int id) throws IOException {
            List<List<String>> exceptions = new ArrayList<>();

//            User user = authRepository.findByEmail(email);
//            Long userId = user.getId();

            Scanner scanner = new Scanner(file.getInputStream());
            scanner.nextLine();
            while(scanner.hasNext()){
                List<String> list = new ArrayList<>();
                String row = scanner.nextLine();
                String[] rowData = row.split(",");
                if (rowData.length < 5){
                    list.add("Some of the fields are empty");
                }
                Double amount = 0.0;
                try{
                    amount = Double.valueOf(rowData[0]);
                }catch (NumberFormatException e){
                    list.add("please enter amount in correct format");
                }
                if(amount <= 0.0){
                    list.add("amount is invalid and is negative");
                }

                String note = rowData[1];
                String categoryName = rowData[2].toLowerCase().trim();
                if (categoryName.isEmpty()){
                    list.add("Category is not defined");
                }
                LocalDate date = LocalDate.now();
                try {
                    date = LocalDate.parse(rowData[3]);
                }catch (DateTimeParseException e){
                    list.add("Please add date in correct format");
                }
                if(date.isAfter(LocalDate.now()))    list.add("Invalid date");
                String type = "";
                try{
                   type = rowData[4].toUpperCase().trim();
                } catch (Exception e) {
                    list.add("Type is not present");
                }
                if(!type.equals("INCOME") && !type.equals("EXPENSE")){
                    list.add("type is not specified");
                }
                logger.info("Row is {}, {}, {}, {}, {}", amount, note, categoryName, date, type);
                if(!list.isEmpty()){
                    exceptions.add(list);
                    continue;
                }
                Category result = categoryRepository.findCategoryByNameAndTypeAndUserId(categoryName,id,type);
                if(result != null){
                    logger.info(result.toString());
                }else{
//                    Category temp = new Category();
//                    temp.setCategoryName(categoryName);
//                    temp.setDescription(DEFAULT_CATEGORY_DESCRIPTION);
//                    temp.setIconUrl(DEFAULT_ICON_URL);
//                    temp.setTransactionType(type);
                    categoryRepository.createCategory(id,categoryName, DEFAULT_CATEGORY_DESCRIPTION,DEFAULT_ICON_URL,type);
                    result = categoryRepository.findCategoryByNameAndTypeAndUserId(categoryName, id, type);
                }

                Transaction transaction = new Transaction();
                transaction.setNotes(note);
                transaction.setCategoryId(result.getCategoryId());
                transaction.setDateOfTransaction(date);
                transaction.setAmount(amount);

                this.createTransaction( transaction,id);
                exceptions.add(list);
            }
            return exceptions;
        }

    }






