package com.sgt.expense_tracker.Exceptions.Cateogory;

public class InvalidCategory extends RuntimeException {
    public InvalidCategory(String message) {
        super(message);
    }
}
