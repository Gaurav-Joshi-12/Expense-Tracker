package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthService authService = new AuthService();

//    @PutMapping("/register")
//    public ResponseEntity<Map<String,Object>> registerUser(){
//
//    }
}
