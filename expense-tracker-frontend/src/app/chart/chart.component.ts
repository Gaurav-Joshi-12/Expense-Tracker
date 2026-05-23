import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { ChartService, ChartDTO } from '../chart.service';

Chart.register(...registerables);

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  incomeChart: any;
  expenseChart: any;
  incomeVsExpenseChart: any;
  savingsChart: any;

  isLoading = true;
  errorMessage = '';

  // Color palettes
  private pieColors = [
    '#6366f1', '#8b5cf6', '#a78bfa', '#c4b5fd',
    '#ec4899', '#f472b6', '#f9a8d4',
    '#14b8a6', '#2dd4bf', '#5eead4',
    '#f59e0b', '#fbbf24', '#fcd34d',
    '#ef4444', '#f87171', '#fca5a5'
  ];

  constructor(private chartService: ChartService) {}

  ngOnInit(): void {
    this.loadChartData();
  }

  loadChartData(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.chartService.getChartData().subscribe({
      next: (data: ChartDTO) => {
        this.isLoading = false;
        // Let Angular render the *ngIf canvases into the DOM first
        setTimeout(() => {
          this.createIncomePieChart(data.pieChartIncomeList);
          this.createExpensePieChart(data.pieChartExpenseList);
          this.createIncomeVsExpenseChart(data.lineChartModelsList);
          this.createSavingsChart(data.lineChartSavingsModelList);
        }, 0);
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = 'Failed to load chart data. Please try again.';
        console.error('Chart data error:', err);
      }
    });
  }

  // ── Income Pie Chart ──────────────────────────────────────────
  createIncomePieChart(incomeList: any[]): void {
    if (!incomeList || incomeList.length === 0) return;

    const labels = incomeList.map(item => item.category);
    const amounts = incomeList.map(item => item.amount);
    const total = amounts.reduce((sum, val) => sum + val, 0);

    if (this.incomeChart) this.incomeChart.destroy();

    this.incomeChart = new Chart('incomePieChart', {
      type: 'doughnut',
      data: {
        labels: labels,
        datasets: [{
          data: amounts,
          backgroundColor: this.pieColors.slice(0, labels.length),
          borderWidth: 2,
          borderColor: '#ffffff',
          hoverBorderWidth: 3,
          hoverOffset: 8
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'bottom',
            labels: {
              padding: 16,
              usePointStyle: true,
              pointStyleWidth: 12,
              font: { size: 12, family: 'Arial, sans-serif' }
            }
          },
          tooltip: {
            backgroundColor: 'rgba(0,0,0,0.8)',
            titleFont: { size: 13 },
            bodyFont: { size: 12 },
            padding: 12,
            cornerRadius: 8,
            callbacks: {
              label: (ctx: any) => {
                const value = ctx.raw;
                const pct = ((value / total) * 100).toFixed(1);
                return ` ${ctx.label}: ₹${value.toLocaleString('en-IN')} (${pct}%)`;
              }
            }
          }
        },
        cutout: '55%'
      }
    });
  }

  // ── Expense Pie Chart ─────────────────────────────────────────
  createExpensePieChart(expenseList: any[]): void {
    if (!expenseList || expenseList.length === 0) return;

    const labels = expenseList.map(item => item.category);
    const amounts = expenseList.map(item => item.amount);
    const total = amounts.reduce((sum, val) => sum + val, 0);

    // Use a different color offset for distinction
    const colors = [
      '#ef4444', '#f97316', '#f59e0b', '#eab308',
      '#84cc16', '#22c55e', '#14b8a6', '#06b6d4',
      '#3b82f6', '#6366f1', '#8b5cf6', '#a855f7',
      '#d946ef', '#ec4899', '#f43f5e', '#fb923c'
    ];

    if (this.expenseChart) this.expenseChart.destroy();

    this.expenseChart = new Chart('expensePieChart', {
      type: 'doughnut',
      data: {
        labels: labels,
        datasets: [{
          data: amounts,
          backgroundColor: colors.slice(0, labels.length),
          borderWidth: 2,
          borderColor: '#ffffff',
          hoverBorderWidth: 3,
          hoverOffset: 8
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'bottom',
            labels: {
              padding: 16,
              usePointStyle: true,
              pointStyleWidth: 12,
              font: { size: 12, family: 'Arial, sans-serif' }
            }
          },
          tooltip: {
            backgroundColor: 'rgba(0,0,0,0.8)',
            titleFont: { size: 13 },
            bodyFont: { size: 12 },
            padding: 12,
            cornerRadius: 8,
            callbacks: {
              label: (ctx: any) => {
                const value = ctx.raw;
                const pct = ((value / total) * 100).toFixed(1);
                return ` ${ctx.label}: ₹${value.toLocaleString('en-IN')} (${pct}%)`;
              }
            }
          }
        },
        cutout: '55%'
      }
    });
  }

  // ── Income vs Expense Line Chart ──────────────────────────────
  createIncomeVsExpenseChart(lineData: any[]): void {
    if (!lineData || lineData.length === 0) return;

    const labels = lineData.map(item => item.monthYear);
    const incomeData = lineData.map(item => item.income);
    const expenseData = lineData.map(item => item.expense);

    if (this.incomeVsExpenseChart) this.incomeVsExpenseChart.destroy();

    this.incomeVsExpenseChart = new Chart('incomeVsExpenseChart', {
      type: 'line',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Income',
            data: incomeData,
            borderColor: '#22c55e',
            backgroundColor: 'rgba(34, 197, 94, 0.1)',
            fill: true,
            tension: 0.4,
            borderWidth: 3,
            pointBackgroundColor: '#22c55e',
            pointBorderColor: '#ffffff',
            pointBorderWidth: 2,
            pointRadius: 5,
            pointHoverRadius: 8
          },
          {
            label: 'Expense',
            data: expenseData,
            borderColor: '#ef4444',
            backgroundColor: 'rgba(239, 68, 68, 0.1)',
            fill: true,
            tension: 0.4,
            borderWidth: 3,
            pointBackgroundColor: '#ef4444',
            pointBorderColor: '#ffffff',
            pointBorderWidth: 2,
            pointRadius: 5,
            pointHoverRadius: 8
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        interaction: {
          mode: 'index',
          intersect: false
        },
        plugins: {
          legend: {
            position: 'top',
            labels: {
              padding: 20,
              usePointStyle: true,
              pointStyleWidth: 12,
              font: { size: 13, family: 'Arial, sans-serif', weight: 'bold' }
            }
          },
          tooltip: {
            backgroundColor: 'rgba(0,0,0,0.85)',
            titleFont: { size: 14 },
            bodyFont: { size: 13 },
            padding: 14,
            cornerRadius: 8,
            callbacks: {
              label: (ctx: any) => {
                return ` ${ctx.dataset.label}: ₹${ctx.raw.toLocaleString('en-IN')}`;
              }
            }
          }
        },
        scales: {
          x: {
            grid: { display: false },
            ticks: {
              font: { size: 11 },
              maxRotation: 45,
              minRotation: 0
            }
          },
          y: {
            beginAtZero: true,
            grid: { color: 'rgba(0,0,0,0.06)' },
            ticks: {
              font: { size: 11 },
              callback: (value: any) => '₹' + value.toLocaleString('en-IN')
            }
          }
        }
      }
    });
  }

  // ── Savings Line Chart ────────────────────────────────────────
  createSavingsChart(savingsData: any[]): void {
    if (!savingsData || savingsData.length === 0) return;

    const labels = savingsData.map(item => item.monthYear);
    const amounts = savingsData.map(item => item.amount);

    // Build gradient color array: green for positive savings, red for negative
    const pointColors = amounts.map(val => val >= 0 ? '#22c55e' : '#ef4444');

    if (this.savingsChart) this.savingsChart.destroy();

    this.savingsChart = new Chart('savingsChart', {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [{
          label: 'Net Savings',
          data: amounts,
          backgroundColor: amounts.map(val =>
            val >= 0 ? 'rgba(34, 197, 94, 0.7)' : 'rgba(239, 68, 68, 0.7)'
          ),
          borderColor: amounts.map(val =>
            val >= 0 ? '#22c55e' : '#ef4444'
          ),
          borderWidth: 2,
          borderRadius: 6,
          hoverBackgroundColor: amounts.map(val =>
            val >= 0 ? 'rgba(34, 197, 94, 0.9)' : 'rgba(239, 68, 68, 0.9)'
          )
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'top',
            labels: {
              padding: 20,
              usePointStyle: true,
              pointStyleWidth: 12,
              font: { size: 13, family: 'Arial, sans-serif', weight: 'bold' }
            }
          },
          tooltip: {
            backgroundColor: 'rgba(0,0,0,0.85)',
            titleFont: { size: 14 },
            bodyFont: { size: 13 },
            padding: 14,
            cornerRadius: 8,
            callbacks: {
              label: (ctx: any) => {
                const val = ctx.raw;
                const prefix = val >= 0 ? '+' : '';
                return ` Savings: ${prefix}₹${val.toLocaleString('en-IN')}`;
              }
            }
          }
        },
        scales: {
          x: {
            grid: { display: false },
            ticks: {
              font: { size: 11 },
              maxRotation: 45,
              minRotation: 0
            }
          },
          y: {
            grid: { color: 'rgba(0,0,0,0.06)' },
            ticks: {
              font: { size: 11 },
              callback: (value: any) => '₹' + value.toLocaleString('en-IN')
            }
          }
        }
      }
    });
  }
}
