package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.Model.ChartDTO;
import com.sgt.expense_tracker.Service.ChartService;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChartController {

    @Autowired
    ChartService chartService;

    @GetMapping("/chart")
    public ChartDTO getChartsData(org.springframework.security.core.Authentication auth ){
        ChartDTO chartDTO = new ChartDTO();
        chartDTO.setPieChartIncomeList(chartService.getPieChartIncomeData(auth));
        chartDTO.setPieChartExpenseList(chartService.getPieChartExpenseData(auth));
        chartDTO.setLineChartModelsList(chartService.getLineChartData(auth));
        chartDTO.setLineChartSavingsModelList(chartService.getLineChartSavingsData(auth));
        chartService.getLineChartSavingsData(auth);
        chartService.getStackedBarChartData(auth);

        return chartDTO;

    }
}
