package com.sgt.expense_tracker.Exceptions.Cateogory;

public class CategoryExists extends RuntimeException {
    public CategoryExists(String message) {
        super(message);
    }
}
