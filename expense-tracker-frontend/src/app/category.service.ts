import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from './category/category-page/category.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  isEditOn = false
  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) {}

  // ✅ Get all categories of a user
  getAll(): Observable<Category[]> {
    return this.http.get<Category[]>(
      `${this.baseUrl}/all-category`,{withCredentials:true}
    ); 
  }

  // ✅ Create category
  createCategory(userId: number, category: Category): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/create-category`,
      category,{withCredentials:true}
    );
  }

  updateCategory(categoryId: number, category: Category): Observable<any> {
    return this.http.put(
      `${this.baseUrl}/update-category/${categoryId}`,
      category,
      { withCredentials: true }
    );
    
  }

  // ✅ Update transaction type
  updateTransactionType(categoryId: number, transactionType: string): Observable<any> {
    return this.http.put(
      `${this.baseUrl}/update-transaction-type/${categoryId}`,
      { transactionType },{withCredentials:true}
    );
  }
  

  // ✅ Soft delete category
  deleteCategory(categoryId: number): Observable<any> {
    return this.http.put(
      `${this.baseUrl}/delete-category/${categoryId}`,{},
      {withCredentials:true}
    );
  }
  
}
