package com.sgt.expense_tracker.Exceptions.User;

public class InvalidUser extends RuntimeException {
    public InvalidUser() {
        super("No user exists with this email");
    }
}
