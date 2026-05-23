import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PieChartModel {
  category: string;
  amount: number;
}

export interface LineChartModel {
  monthYear: string;
  income: number;
  expense: number;
}

export interface LineChartSavingsModel {
  monthYear: string;
  amount: number;
}

export interface ChartDTO {
  pieChartIncomeList: PieChartModel[];
  pieChartExpenseList: PieChartModel[];
  lineChartModelsList: LineChartModel[];
  lineChartSavingsModelList: LineChartSavingsModel[];
}

@Injectable({
  providedIn: 'root'
})
export class ChartService {

  private apiUrl = 'http://localhost:8081/api/chart';

  constructor(private http: HttpClient) {}

  getChartData(): Observable<ChartDTO> {
    return this.http.get<ChartDTO>(this.apiUrl, { withCredentials: true });
  }
}
