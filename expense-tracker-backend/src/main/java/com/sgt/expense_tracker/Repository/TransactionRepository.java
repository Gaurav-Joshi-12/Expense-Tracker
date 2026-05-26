package com.sgt.expense_tracker.Repository;

import com.sgt.expense_tracker.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void createTransaction(int userId, int categoryId, double amount, LocalDate dateOfTransaction, String notes) {
        String query = "INSERT INTO TRANSACTIONS (user_id, category_id,amount,date_of_transaction,notes) VALUES (?,?,?,?,?)";

        jdbcTemplate.update(query,userId,categoryId,amount,dateOfTransaction,notes);
    }

    public List<Transaction> getTransaction(int userId, String category, LocalDate start, LocalDate end, String type,String sortColumn, String sortDir,Integer rowsPerPage,Integer pageNo){


        System.out.println(category+" "+start+" "+end+" "+type);
        StringBuilder query = new StringBuilder("select t.transaction_id, t.user_id, t.amount , t.category_id, t.notes, t.date_of_transaction, t.active_yn, c.category_name, c.transaction_type from transactions t inner join categories c on t.category_id = c.category_id where t.user_id = ? and c.active_yn = 1");


        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);
        if(category!=null){
            query.append(" and c.category_name = ?");
            params.add(category);
        }
        if(type!=null){
            query.append(" and c.transaction_type = ?");
            params.add(type);
        }
        if(start!=null && end==null){
            query.append(" and t.date_of_transaction between ? and ?");
            params.add(start);
            params.add(LocalDate.now());
        }
        if(start==null && end!=null){
            query.append(" and t.date_of_transaction between ? and ?");
            params.add(end.minusDays(30));
            params.add(end);
        }
        if(start!=null && end!=null){
            query.append(" and t.date_of_transaction between ? and ?");
            params.add(start);
            params.add(end);
        }
        if("date_of_transaction".equals(sortColumn))    sortColumn = "t.date_of_transaction";
        if("amount".equals(sortColumn)) sortColumn = "t.amount";

        if(sortColumn!=null){
            query.append(" order by "+sortColumn+" "+sortDir);
//            params.add(sortColumn);
//            params.add(sortDir);
        }

        if(rowsPerPage!=null && pageNo!=null){
            int offset = (pageNo-1)*rowsPerPage;
            query.append(" Limit "+rowsPerPage+" Offset "+offset);
        }


        System.out.println(query);
//        System.out.println(params);
        return jdbcTemplate.query(query.toString(),(resultSet,rowNum)->{
                Transaction t = new Transaction();
                t.setTransactionId(resultSet.getInt("transaction_id"));
                t.setUserId(resultSet.getInt("user_id"));
                t.setCategoryId(resultSet.getInt("category_id"));
                t.setAmount(resultSet.getDouble("amount"));
                java.sql.Date dateVal = resultSet.getDate("date_of_transaction");
                t.setDateOfTransaction(dateVal != null ? dateVal.toLocalDate() : null);
                t.setActiveYn(resultSet.getInt("active_yn"));
                t.setNotes(resultSet.getString("notes"));
                t.setCategoryName(resultSet.getString("category_name"));
                t.setCategoryType(resultSet.getString("transaction_type"));
                return t;
            },params.toArray());

//        return transactions;

    }


}
