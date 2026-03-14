// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { ActivatedRoute, Router } from '@angular/router';
// import { TransactionService } from 'src/app/transaction.service';
// import { CategoryService } from 'src/app/category.service';

// @Component({
//   selector: 'app-transaction',
//   templateUrl: './transaction.component.html',
//   styleUrls: ['./transaction.component.css']
// })
// export class TransactionComponent implements OnInit{

//   transactionForm!: FormGroup;
//   transactions: any[] = [];
//   categories: any[] = [];
  
//   editingTransactionId: number | null = null;
//   isEditOn = false; // Kept local to the component for cleaner state management
  
//   constructor(
//     private transactionService: TransactionService,
//     private categoryService: CategoryService, // Needed to load categories for the dropdown
//     private fb: FormBuilder, 
//     private activatedRoute: ActivatedRoute,
//     private router:Router
//   ) {}

//   ngOnInit(): void {
//     this.loadCategories();
//     this.loadTransactions();
    
//     this.transactionForm = this.fb.group({
//       transactionType: ['EXPENSE', Validators.required],
//       amount: ['', [Validators.required, Validators.min(0.01)]],
//       categoryId: ['', Validators.required],
//       transactionDate: [this.getTodayDateString(), Validators.required],
//       notes: ['']
//     });

//     // Optional: Reset category selection when switching between Income/Expense
//     this.transactionForm.get('transactionType')?.valueChanges.subscribe(() => {
//       this.transactionForm.patchValue({ categoryId: '' });
//     });
//   }

//   submitTransaction() {
//     console.log(this.transactionForm.value);
//     let value = this.transactionForm.value;
    
//     // let userId = this.activatedRoute.snapshot.paramMap.get("token");
//     let userId = 1; // Hardcoded exactly like your category component

//     if (!this.isEditOn) {
//       this.transactionService.createTransaction(userId, value).subscribe({
//         next: () => {
//           console.log("Transaction Created Successfully");
//           alert("Transaction created successfully");
//           this.resetForm();
//           this.loadTransactions();
//         },
//         error: (err: any) => {
//           console.log(err);
//           alert(err.error?.body || "Error creating transaction");
//         }
//       });
//     }
    
//     if (this.isEditOn && this.editingTransactionId) {
//       this.transactionService.updateTransaction(this.editingTransactionId, value).subscribe({
//         next: () => {
//           console.log("Transaction updated Successfully");
//           alert("Transaction updated successfully");
//           this.resetForm();
//           this.loadTransactions();
//         },
//         error: (err: any) => {
//           console.log(err);
//           alert(err.error?.body || "Error updating transaction");
//         }
//       });
//     }
//   }

//   editTransaction(t: any) {
//     this.isEditOn = true;
//     this.editingTransactionId = t.transactionId; // Assuming your DB primary key is transactionId
    
//     this.transactionForm.patchValue({
//       transactionType: t.transactionType,
//       amount: t.amount,
//       categoryId: t.categoryId, // Ensure the category dropdown matches this ID
//       transactionDate: t.transactionDate, // Make sure date format matches YYYY-MM-DD
//       notes: t.notes
//     });
//   }

//   deleteTransaction(id: number) {
//     if (!confirm("Are you sure you want to delete this transaction?")) {
//       return;
//     }
  
//     this.transactionService.deleteTransaction(id).subscribe({
//       next: () => {
//         console.log("Transaction Deleted");
//         alert("Transaction deleted successfully");
//         this.loadTransactions();
//       },
//       error: (err: any) => {
//         console.log(err);
//         alert(err.error?.body || "Error deleting transaction");
//       }
//     });
//   }

//   resetForm() {
//     this.transactionForm.reset({
//       transactionType: 'EXPENSE',
//       transactionDate: this.getTodayDateString(),
//       categoryId: ''
//     });
//     this.isEditOn = false;
//     this.editingTransactionId = null;
//   }
  
//   loadTransactions() {
//     // Assuming you fetch transactions for a specific user ID = 1
//     let userId = 1;
//     this.transactionService.getAllTransactions(userId).subscribe({
//       next: (res: any) => {
//         this.transactions = res;
//       },
//       error: (err: any) => {
//         console.log(err);
//         alert("Error loading transactions");
//       }
//     });
//   }

//   loadCategories() {
//     // Load categories to populate the select dropdown in the HTML
//     this.categoryService.getAll().subscribe({
//       next: (res: any) => {
//         this.categories = res;
//       },
//       error: (err: any) => {
//         console.log(err);
//         console.error("Error loading categories for dropdown");
//       }
//     });
//   }

//   /**
//    * Helper to format today's date for the HTML <input type="date"> (YYYY-MM-DD)
//    */
//   private getTodayDateString(): string {
//     const today = new Date();
//     const year = today.getFullYear();
//     const month = String(today.getMonth() + 1).padStart(2, '0');
//     const day = String(today.getDate()).padStart(2, '0');
//     return `${year}-${month}-${day}`;
//   }

//   /**
//    * Navigate to or open a modal for creating a new category
//    */
//   openNewCategoryModal(): void {
//     // This simply redirects the user to your existing Category page
//     this.router.navigate(['/category']); 
//   }

// }

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TransactionService } from 'src/app/transaction.service';
import { CategoryService } from 'src/app/category.service';

@Component({
  selector: 'app-transaction-page',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {

  transactionForm!: FormGroup;
  transactions: any[] = [];
  categories: any[] = [];
  
  editingTransactionId: number | null = null;
  isEditOn = false; 
  
  constructor(
    private transactionService: TransactionService,
    private categoryService: CategoryService, 
    private fb: FormBuilder, 
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadCategories();
    this.loadTransactions();
    
    // Initialized without transactionType
    this.transactionForm = this.fb.group({
      amount: ['', [Validators.required, Validators.min(0.01)]],
      categoryId: ['', Validators.required],
      transactionDate: [this.getTodayDateString(), Validators.required],
      notes: ['']
    });
  }

  submitTransaction() {
    console.log(this.transactionForm.value);
    let value = this.transactionForm.value;
    
    let userId = 1; // Hardcoded exactly like your category component

    if (!this.isEditOn) {
      this.transactionService.createTransaction(userId, value).subscribe({
        next: () => {
          console.log("Transaction Created Successfully");
          alert("Transaction created successfully");
          this.resetForm();
          this.loadTransactions();
        },
        error: (err: any) => {
          console.log(err);
          alert(err.error?.body || "Error creating transaction");
        }
      });
    }
    
    if (this.isEditOn && this.editingTransactionId) {
      this.transactionService.updateTransaction(this.editingTransactionId, value).subscribe({
        next: () => {
          console.log("Transaction updated Successfully");
          alert("Transaction updated successfully");
          this.resetForm();
          this.loadTransactions();
        },
        error: (err: any) => {
          console.log(err);
          alert(err.error?.body || "Error updating transaction");
        }
      });
    }
  }

  editTransaction(t: any) {
    this.isEditOn = true;
    this.editingTransactionId = t.transactionId; 
    
    this.transactionForm.patchValue({
      amount: t.amount,
      categoryId: t.categoryId,
      transactionDate: t.transactionDate, 
      notes: t.notes
    });
  }

  deleteTransaction(id: number) {
    if (!confirm("Are you sure you want to delete this transaction?")) {
      return;
    }
  
    this.transactionService.deleteTransaction(id).subscribe({
      next: () => {
        console.log("Transaction Deleted");
        alert("Transaction deleted successfully");
        this.loadTransactions();
      },
      error: (err: any) => {
        console.log(err);
        alert(err.error?.body || "Error deleting transaction");
      }
    });
  }

  resetForm() {
    this.transactionForm.reset({
      transactionDate: this.getTodayDateString(),
      categoryId: ''
    });
    this.isEditOn = false;
    this.editingTransactionId = null;
  }
  
  loadTransactions() {
    let userId = 1;
    this.transactionService.getAllTransactions(userId).subscribe({
      next: (res: any) => {
        this.transactions = res;
      },
      error: (err: any) => {
        console.log(err);
        alert("Error loading transactions");
      }
    });
  }

  loadCategories() {
    this.categoryService.getAll().subscribe({
      next: (res: any) => {
        this.categories = res;
      },
      error: (err: any) => {
        console.log(err);
        console.error("Error loading categories for dropdown");
      }
    });
  }

  private getTodayDateString(): string {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}