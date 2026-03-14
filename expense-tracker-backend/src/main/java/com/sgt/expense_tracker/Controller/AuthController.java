package com.sgt.expense_tracker.Controller;

import com.sgt.expense_tracker.Exceptions.User.EmailAlreadyExistsException;
import com.sgt.expense_tracker.Exceptions.User.InvalidEmail;
import com.sgt.expense_tracker.Exceptions.User.UsernameAlreadyExistsException;
import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthService authService = new AuthService();

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user){
        try{
            authService.registerUser(user);
        } catch (EmailAlreadyExistsException | UsernameAlreadyExistsException | InvalidEmail  e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));


        }
        return ResponseEntity.ok().body(Map.of("body","Inserted"));

    }


//    @GetMapping("/login")
//    public ResponseEntity<?> loggedInUser(@RequestBody User user){
//        try{
//            logger.info("Reached login");
//            User user1 = authService.userLogin(user.getUsername(), user.getPassword());
//            return ResponseEntity.ok().body(Map.of("body","Logged In Successfully"));
//        } catch (LoginException e) {
//            logger.info(e.getMessage());
//            return ResponseEntity.badRequest().body(Map.of("body","Not valid username or password"));
//        }
//    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody Map<String,String> body){
        try{
            String email = body.get("email");
            authService.forgotPassword(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @PostMapping("/validate-token")
//    public void validateToken(@RequestBody Map<String,String> body){
//        try{
//            String token = body.get("token");
//            authService.validateToken(token);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String,String> body){
        try{
            String token = body.get("token");
            authService.validateToken(token);
            authService.resetPassword(body.get("token"),body.get("newPassword"));
            return ResponseEntity.ok().body(Map.of("body","Password is Reseted"));
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResponseEntity.badRequest().body(Map.of("body",e.getMessage()));
        }
    }





}
