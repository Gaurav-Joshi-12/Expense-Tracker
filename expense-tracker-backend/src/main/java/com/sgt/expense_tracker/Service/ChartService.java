package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Model.LineChartModel;
import com.sgt.expense_tracker.Model.LineChartSavingsModel;
import com.sgt.expense_tracker.Model.PieChartModel;
import com.sgt.expense_tracker.Repository.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartService {

    @Autowired
    ChartRepository chartRepository;

    @Autowired
    AuthService authService;

    public List<PieChartModel> getPieChartIncomeData(org.springframework.security.core.Authentication auth){
        try{
            int id = authService.findUserByEmail(auth.getName()).getUserId();
            return chartRepository.getPieChartIncomeData(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<PieChartModel> getPieChartExpenseData(Authentication auth) {
        try{
            int id = authService.findUserByEmail(auth.getName()).getUserId();
            return chartRepository.getPieChartExpenseData(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    public List<LineChartModel> getLineChartData(org.springframework.security.core.Authentication auth){
        try{
            int id = authService.findUserByEmail(auth.getName()).getUserId();
            return chartRepository.getLineChartData(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getStackedBarChartData(org.springframework.security.core.Authentication auth){
        try{
            int id = authService.findUserByEmail(auth.getName()).getUserId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<LineChartSavingsModel> getLineChartSavingsData(Authentication auth) {
        try{
            int id = authService.findUserByEmail(auth.getName()).getUserId();
            return chartRepository.getSavingsLineChart(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
