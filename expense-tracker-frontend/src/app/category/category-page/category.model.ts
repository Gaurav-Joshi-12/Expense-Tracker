// category.model.ts
export interface Category {
    categoryId?: number;
    categoryName: string;
    description: string;
    iconUrl: string;
    transactionType: 'INCOME' | 'EXPENSE';
    activeYn: number;
  }
  