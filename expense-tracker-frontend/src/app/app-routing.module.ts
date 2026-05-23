import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ForgotPassComponent } from './auth/forgot-pass/forgot-pass.component';
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { CategoryPageComponent } from './category/category-page/category-page.component';
import { TransactionComponent } from './transaction/transaction.component';
import { ChartComponent } from './chart/chart.component';





const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot-password', component:ForgotPassComponent },
  { path: 'reset/:token', component: ResetPasswordComponent },
  {path: 'category', component: CategoryPageComponent },
  {path: 'transaction', component: TransactionComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {path : 'chart', component: ChartComponent },
  { path: '**', redirectTo: 'login' },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { 

}
