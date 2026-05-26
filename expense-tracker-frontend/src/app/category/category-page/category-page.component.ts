import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from 'src/app/category.service';

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html',
  styleUrls: ['./category-page.component.css']
})
export class CategoryPageComponent implements OnInit {

  categoryForm!: FormGroup;
  categories: any[] = [];
  editingCategoryId: number | null = null;

  constructor(private categoryService: CategoryService, private fb: FormBuilder, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadCategories()
    this.categoryForm = this.fb.group({
      categoryName: ['', Validators.required],
      description: [''],
      iconUrl: [''],
      transactionType: ['EXPENSE', Validators.required],
      activeYn: [1]
    });
  }

  isEditOn = false
  submit() {
    console.log(this.categoryForm.value);
    let value = this.categoryForm.value;
    // let id =  this.activatedRoute.snapshot.paramMap.get("token");
    let id = 1
    if (!this.categoryService.isEditOn) {
      this.categoryService.createCategory(id, value).subscribe({
        next: () => {
          console.log("Category Created Successfully");
          alert("Category created successfully");
          this.resetForm();
          this.loadCategories();
        },
        error: (err: any) => {
          console.log(err);
          alert(err.error?.body || "Error creating category");
        }
      });
    }
    if (this.categoryService.isEditOn) {
      this.categoryService.updateCategory(id, value).subscribe({
        next: () => {
          console.log("Category updated Successfully");
          alert("Category updated successfully");
          this.resetForm();
          this.loadCategories();
          this.categoryService.isEditOn = false;
        },
        error: (err: any) => {
          console.log(err);
          alert(err.error?.body || "Error updating category");
        }
      });
    }
  }


  edit(c: any) {
    this.categoryService.isEditOn = true;
    this.editingCategoryId = c.categoryId;
    this.categoryForm.patchValue({
      categoryName: c.categoryName,
      description: c.description,
      iconUrl: c.iconUrl,
      transactionType: c.transactionType,
      activeYn: c.activeYn
    });
  }

  delete(id: number) {
    if (!confirm("Are you sure you want to delete this category?")) {
      return;
    }

    this.categoryService.deleteCategory(id).subscribe({
      next: () => {
        console.log("Category Deleted");
        alert("Category deleted successfully");
        this.loadCategories();
      },
      error: (err: any) => {
        console.log(err);
        alert(err.error?.body || "Error deleting category");
      }
    });
  }

  resetForm() {
    this.categoryForm.reset({
      transactionType: 'EXPENSE',
      activeYn: 1
    });
    this.editingCategoryId = null;
  }

  loadCategories() {

    this.categoryService.getAll().subscribe({
      next: (res: any) => {
        this.categories = res;
      },
      error: (err: any) => {
        console.log(err);
        alert("Error loading categories");
      }
    });
  }

}
