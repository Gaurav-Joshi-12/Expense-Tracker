package com.sgt.expense_tracker.Repository;

import com.sgt.expense_tracker.Model.LineChartModel;
import com.sgt.expense_tracker.Model.LineChartSavingsModel;
import com.sgt.expense_tracker.Model.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChartRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<PieChartModel> getPieChartIncomeData(int userId){
        String sql = "select c.category_name as CATEGORY,sum(t.amount) as AMOUNT from categories c\n" +
                "join transactions t\n" +
                "on c.category_id = t.category_id\n" +
                "where c.transaction_type = 'INCOME'\n" +
                "and c.user_id = ?\n" +
                "and c.active_yn = 1\n" +
                "group by c.category_name";

        List<PieChartModel> results = jdbcTemplate.query(sql,(rs,rowNum)->{
            PieChartModel pieChartModel = new PieChartModel();
            pieChartModel.setAmount(rs.getDouble("AMOUNT"));
            pieChartModel.setCategory(rs.getString("CATEGORY"));
            return pieChartModel;
        },userId);

        return results;
    }

    public List<PieChartModel> getPieChartExpenseData(int userId){
        String sql = "select c.category_name as CATEGORY,sum(t.amount) as AMOUNT from categories c\n" +
                "join transactions t\n" +
                "on c.category_id = t.category_id\n" +
                "where c.transaction_type = 'EXPENSE'\n" +
                "and c.user_id = ?\n" +
                "and c.active_yn = 1\n" +
                "group by c.category_name";

        List<PieChartModel> results = jdbcTemplate.query(sql,(rs,rowNum)->{
            PieChartModel pieChartModel = new PieChartModel();
            pieChartModel.setAmount(rs.getDouble("AMOUNT"));
            pieChartModel.setCategory(rs.getString("CATEGORY"));
            return pieChartModel;
        },userId);
        return results;
    }

    public List<LineChartModel> getLineChartData(int userId){
        String sql = "select date_format(date_of_transaction,'%b %Y') as month_year,\n" +
                "sum(CASE WHEN c.transaction_type='EXPENSE' THEN t.amount ELSE 0 END) as EXPENSE,\n" +
                "sum(CASE WHEN c.transaction_type='INCOME' THEN t.amount ELSE 0 END) as INCOME\n" +
                "from transactions t\n" +
                "inner join categories c\n" +
                "on t.category_id = c.category_id\n" +
                "where date_of_transaction >= DATE_SUB(CURDATE(),INTERVAL 1 YEAR)\n" +
                "and c.user_id = ?\n" +
                "and c.active_yn = 1\n" +
                "group by month_year, YEAR(date_of_transaction), MONTH(date_of_transaction)\n" +
                "order by YEAR(date_of_transaction) ASC, MONTH(date_of_transaction) ASC;";

        List<LineChartModel> results = jdbcTemplate.query(sql,(rs,rowNum)->{
            LineChartModel lineChartModel = new LineChartModel();
            lineChartModel.setMonthYear(rs.getString("month_year"));
            lineChartModel.setIncome(rs.getDouble("INCOME"));
            lineChartModel.setExpense(rs.getDouble("EXPENSE"));
            return lineChartModel;
        },userId);

        return results;
    }


    public List<LineChartSavingsModel> getSavingsLineChart(int userId){
        String sql = "select date_format(date_of_transaction,'%b %Y') as month_year,\n" +
                "sum(CASE WHEN c.transaction_type='INCOME' THEN t.amount ELSE 0 END) -\n" +
                "sum(CASE WHEN c.transaction_type='EXPENSE' THEN t.amount ELSE 0 END) as SAVINGS\n" +
                "from transactions t\n" +
                "inner join categories c\n" +
                "on t.category_id = c.category_id\n" +
                "where date_of_transaction >= DATE_SUB(CURDATE(),INTERVAL 1 YEAR)\n" +
                "and c.user_id = ?\n" +
                "and c.active_yn = 1\n" +
                "group by month_year, YEAR(date_of_transaction), MONTH(date_of_transaction)\n" +
                "order by YEAR(date_of_transaction) ASC, MONTH(date_of_transaction) ASC";

        List<LineChartSavingsModel> results = jdbcTemplate.query(sql,(rs, rowNum)->{
            LineChartSavingsModel lineChartSavingsModel = new LineChartSavingsModel();
            lineChartSavingsModel.setAmount(rs.getDouble("SAVINGS"));
            lineChartSavingsModel.setMonthYear(rs.getString("month_year"));
            return lineChartSavingsModel;
        },userId);

        return results;

    }


}





