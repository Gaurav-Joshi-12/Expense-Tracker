package com.sgt.expense_tracker.Exceptions.User;

public class UsernameAlreadyExistsException extends Exception{
    public UsernameAlreadyExistsException(){
        super("Username already Exists");
    }
}
