package com.sgt.expense_tracker.Exceptions.User;

public class InvalidEmail extends Exception{

    public InvalidEmail(String invalidEmail){
        super(invalidEmail);

    }
}
