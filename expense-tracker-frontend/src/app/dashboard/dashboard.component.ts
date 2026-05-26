import { Component } from '@angular/core';

/*
  DashboardComponent — a parent shell that embeds the ChartComponent
  and TransactionComponent side-by-side on a single page.
  It owns the page-level chrome (dark background, glow blobs, header)
  so the children can focus purely on their own content.
*/
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {}
