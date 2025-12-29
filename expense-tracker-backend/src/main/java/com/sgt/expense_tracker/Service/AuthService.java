package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sgt.expense_tracker.Model.User;

@Service
public class AuthService {

    @Autowired
    AuthRepository authRepository = new AuthRepository();


    public void register(User user){
        // check validity of email
        // check if email exists
        // check if username exists
        // hash password
        // if all passed then call repository
    }
}
