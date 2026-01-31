package com.sgt.expense_tracker.Exceptions.User;

public class EmailAlreadyExistsException extends Exception {
    public EmailAlreadyExistsException(){
        super("Email already Exists");
    }
}
