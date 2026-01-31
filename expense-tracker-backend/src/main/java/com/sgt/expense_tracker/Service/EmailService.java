package com.sgt.expense_tracker.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String email,String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        This line will create a message but cant set it to our required email id
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
//        This is a wrapper class in which we pass our created message, and then we can set it.
        String resetLink = token;
        String htmlContent = resetLinkMailHtml(resetLink);
        File image = new File("src/main/resources/static/bhenga.png");
        try{
            mimeMessageHelper.setTo(email);

//            mimeMessageHelper.setText(resetLink);
            // this is boolean true
            mimeMessageHelper.setText(htmlContent,true);
            mimeMessageHelper.setSubject("Reset Password - Expense Tracker");
//            These methods throw a checked exception from Jakarta, so we have to take care
//            of it
            mimeMessageHelper.addAttachment("bhenga.png", image);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }



    }

    public String resetLinkMailHtml(String resetLink){
         String htmlContent =
                "<html>" +
                        "<body style='font-family: Arial, sans-serif;'>" +
                        "<h2>Reset Your Password</h2>" +
                        "<p>Click the link below to reset your password:</p>" +
                        "<a href='" + "http://localhost:4200/reset/"+ resetLink + "' " +
                        "style='display:inline-block;padding:10px 20px;" +
                        "background-color:#2563eb;color:#ffffff;" +
                        "text-decoration:none;border-radius:5px;'>Reset Password</a>" +
                        "<p>This link will expire in 5 minutes.</p>" +
                        "</body>" +
                        "</html>";

        return htmlContent;
    }
}
