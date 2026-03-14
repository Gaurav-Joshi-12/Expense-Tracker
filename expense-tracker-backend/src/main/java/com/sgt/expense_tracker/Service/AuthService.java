package com.sgt.expense_tracker.Service;

import com.sgt.expense_tracker.Exceptions.User.EmailAlreadyExistsException;
import com.sgt.expense_tracker.Exceptions.User.InvalidEmail;
import com.sgt.expense_tracker.Exceptions.User.UsernameAlreadyExistsException;
import com.sgt.expense_tracker.Repository.AuthRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.sgt.expense_tracker.Model.User;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {

    @Autowired
    AuthRepository authRepository = new AuthRepository();

    @Autowired
    EmailService emailService = new EmailService();


    public void registerUser(User user) throws
            EmailAlreadyExistsException, InvalidEmail,UsernameAlreadyExistsException

    {
        // check validity of email -> done
        // check if email exists ->done
        // check if username exists ->done
        // hash password
        // if all passed then call repository

        if(!isEmailValid(user.getEmail())){
            throw new InvalidEmail("Invalid user Email");
        }

        if(authRepository.findByEmail(user.getEmail())!=null){
            throw new EmailAlreadyExistsException();
        }
        if(authRepository.findByUsername(user.getUsername())!=null){
            throw new UsernameAlreadyExistsException();
        }


//        authRepository.addUser(user.getName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getMobileNo());

        System.out.println("Reached here");
        authRepository.save(user.getName(), user.getUsername(), user.getEmail(), encodePassword(user.getPassword()), user.getMobileNo());


    }

    public boolean isEmailValid(String email){
        if(email == null)   return false;
        String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public String encodePassword(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public User userLogin(String username,String password) throws LoginException {
        String encodedPass = encodePassword(password);
        User user = authRepository.userLogin(username,encodedPass);
        if(user == null)    throw new LoginException();
        return user;
    }

    public void forgotPassword(String email) throws InvalidEmail, MessagingException {
        if(!isEmailValid(email)){
            throw new InvalidEmail("Invalid user Email");
        }

        User user = authRepository.findByEmail(email);
        if(user == null)    throw  new InvalidEmail("No email found");
        int userId = user.getUserId();
        authRepository.markTokenUsedForUser(userId);
        String token = UUID.randomUUID().toString();
        LocalDateTime tokenTime = LocalDateTime.now().plusMinutes(5);
        authRepository.saveResetToken(token,tokenTime,userId);
        emailService.sendEmail(email,token);

    }

    public void validateToken(String token){
           Integer val =  authRepository.validateToken(token);
           if(val == null)  throw new RuntimeException("Token is expired or invalid ");
    }


//    public String resetPassword(body.get("token"),body.get("newPassword"));

    public void resetPassword(String token , String password){
        Integer userId =  authRepository.validateToken(token);
        if(userId == null)  throw new RuntimeException("Invalid or expired token");
        String newPass = encodePassword(password);
        authRepository.updatePassword(userId ,newPass);
        authRepository.markTokenUsed(token);

    }

    public User findUserByEmail(String email){
        return authRepository.findByEmail(email);
    }


}
