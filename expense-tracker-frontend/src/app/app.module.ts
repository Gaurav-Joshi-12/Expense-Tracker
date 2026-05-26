import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import{HttpClientModule} from '@angular/common/http';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ForgotPassComponent } from './auth/forgot-pass/forgot-pass.component';
import { AuthModuleModule } from './auth/auth.module';
import { CategoryPageComponent } from './category/category-page/category-page.component';
import { TransactionComponent } from './transaction/transaction.component';
import { ChartComponent } from './chart/chart.component';
/* NEW: DashboardComponent wraps Chart + Transaction into one page */
import { DashboardComponent } from './dashboard/dashboard.component';
import { NavbarComponent } from './navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ForgotPassComponent,
    CategoryPageComponent,
    TransactionComponent,
    ChartComponent,
    DashboardComponent,     /* NEW: register the dashboard shell */
    NavbarComponent         /* NEW: register navbar */
  ],
  imports: [
    AuthModuleModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
