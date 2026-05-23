// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class TransactionService {

//   constructor() { }
// }

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  // Replace this with your actual backend API URL
  private apiUrl = 'http://localhost:8081/api/transaction'; 

  constructor(private http: HttpClient) { }

  /**
   * READ: Get all transactions for a specific user
   */
  getAllTransactions(): Observable<any> {
    // Example endpoint: GET /api/transactions/user/1
    return this.http.get(`${this.apiUrl}`,{withCredentials:true});
  }

  /**
   * CREATE: Add a new transaction
   */
  createTransaction(transactionData: any): Observable<any> {
    // Example endpoint: POST /api/transactions/user/1
    return this.http.post(`${this.apiUrl}`, transactionData,{withCredentials:true});
  }

  /**
   * UPDATE: Edit an existing transaction
   */
  updateTransaction(transactionId: number, transactionData: any): Observable<any> {
    // Example endpoint: PUT /api/transactions/15
    return this.http.put(`${this.apiUrl}/${transactionId}`, transactionData,{withCredentials:true});
  }

  /**
   * DELETE: Remove a transaction
   */
  deleteTransaction(transactionId: number): Observable<any> {
    // Example endpoint: DELETE /api/transactions/15
    return this.http.delete(`${this.apiUrl}/${transactionId}`,{withCredentials:true});
  }
  
  /**
   * (Optional but Recommended): Get a summary/total for dashboard
   */
  getTransactionSummary(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/user/${userId}/summary`,{withCredentials:true});
  }

  bulkUpload(file: any) {
    return this.http.post("http://localhost:8081/api/transaction/bulk-upload", file,{withCredentials:true});
  }

}