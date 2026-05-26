import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ForgotPassComponent } from './auth/forgot-pass/forgot-pass.component';
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { CategoryPageComponent } from './category/category-page/category-page.component';
/*
  NEW: DashboardComponent replaces the separate /chart and /transaction
  routes.  Both views are now combined on a single page.
*/
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot-password', component: ForgotPassComponent },
  { path: 'reset/:token', component: ResetPasswordComponent },
  { path: 'category', component: CategoryPageComponent },

  /* NEW: single merged dashboard page */
  { path: 'dashboard', component: DashboardComponent },

  /*
    OLD routes now redirect to /dashboard so any existing links
    (e.g. bookmarks, navigation from other pages) still work.
  */
  { path: 'transaction', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'chart', redirectTo: 'dashboard', pathMatch: 'full' },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
