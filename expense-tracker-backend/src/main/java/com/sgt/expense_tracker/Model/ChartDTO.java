package com.sgt.expense_tracker.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChartDTO {
    List<PieChartModel> pieChartIncomeList;
    List<PieChartModel> pieChartExpenseList;
    List<LineChartModel> lineChartModelsList;
    List<LineChartSavingsModel> lineChartSavingsModelList;
}
