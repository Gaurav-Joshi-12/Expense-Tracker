package com.sgt.expense_tracker.Exceptions.Cateogory;

public class CategoryDoesNotExist extends RuntimeException {
    public CategoryDoesNotExist(String message) {
        super(message);
    }
}
