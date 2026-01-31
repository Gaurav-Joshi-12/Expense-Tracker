package com.sgt.expense_tracker.Service;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {
    @Autowired
    EmailService emailService;
    @Test
    public void sendMailTest() throws MessagingException{
        emailService.sendEmail("gauravmanwani2005@gmail.com","Bhadwe");
    }
}