package com.sgt.expense_tracker.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String email, String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        This line will create a message but cant set it to our required email id
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//        This is a wrapper class in which we pass our created message, and then we can set it.
        String resetLink = token;
        String htmlContent = resetLinkMailHtml(resetLink);
        File image = new File("src/main/resources/static/bhenga.png");
        try {
            mimeMessageHelper.setTo(email);

//            mimeMessageHelper.setText(resetLink);
            // this is boolean true
            mimeMessageHelper.setText(htmlContent, true);
            mimeMessageHelper.setSubject("Reset Password - Expense Tracker");
//            These methods throw a checked exception from Jakarta, so we have to take care
//            of it
//            mimeMessageHelper.addAttachment("bhenga.png", image);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

    public String resetLinkMailHtml(String resetLink) {
        String htmlContent =
                "<html>" +
                        "<body style='font-family: Arial, sans-serif;'>" +
                        "<h2>Reset Your Password</h2>" +
                        "<p>Click the link below to reset your password:</p>" +
                        "<a href='" + "http://localhost:4200/reset/" + resetLink + "' " +
                        "style='display:inline-block;padding:10px 20px;" +
                        "background-color:#2563eb;color:#ffffff;" +
                        "text-decoration:none;border-radius:5px;'>Reset Password</a>" +
                        "<p>This link will expire in 5 minutes.</p>" +
                        "</body>" +
                        "</html>";

        return htmlContent;
    }

    public void sendWeeklyReportEmail(String email, byte[] reportPdf) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // we put multipart as true to send a byte array
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String htmlContent = reportHtmlContent(); // HTML method
        File image = new File("src/main/resources/static/bhenga.png");


        try {
            mimeMessageHelper.setTo(email);

            // Set HTML content
            mimeMessageHelper.setText(htmlContent, true);

            // Subject
            mimeMessageHelper.setSubject("Your Weekly Report - Expense Tracker");

            // Add inline image (important for HTML display)
            mimeMessageHelper.addInline("logoImage", image);

            Resource resource = new ByteArrayResource(reportPdf);
            mimeMessageHelper.addAttachment("Weekly report.pdf",resource);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getWeeklyRange() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM");

        return startDate.format(formatter) + " - " + endDate.format(formatter);
    }

    public String reportHtmlContent() {
        String dateRange = getWeeklyRange();
        String reportHtmlContent =
                "<html>" +
                        "<body style='font-family: Arial, sans-serif; background-color:#f9fafb; padding:20px;'>" +

                        "<div style='max-width:600px;margin:auto;background:#ffffff;" +
                        "padding:20px;border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,0.1); text-align:center;'>" +

                        // 🔹 Logo Image
                        "<img src='cid:logoImage' style='width:100px;margin-bottom:15px;'/>" +

                        "<h2 style='color:#111827;'>Your Weekly Report (" + dateRange + ") 📊</h2>"+

                        "<p style='color:#374151;text-align:left;'>Hello,</p>" +

                        "<p style='color:#374151;text-align:left;'>Your weekly report is now ready. It includes a summary of your activity, performance insights, and important updates from this week.</p>" +

                        "<p style='color:#374151;text-align:left;'>Stay consistent and keep improving your progress!</p>" +

                        "<div style='margin:20px 0;padding:15px;background:#f3f4f6;border-radius:6px;text-align:left;'>" +
                        "<p style='margin:0;color:#111827;'><strong>Highlights:</strong></p>" +
                        "<ul style='color:#374151;padding-left:20px;'>" +
                        "<li>Activity summary</li>" +
                        "<li>Performance insights</li>" +
                        "<li>Weekly progress tracking</li>" +
                        "</ul>" +
                        "</div>" +

                        "<p style='color:#6b7280;text-align:left;'>This is an automated email. Please do not reply.</p>" +

                        "<br/>" +
                        "<p style='color:#111827;text-align:left;'>Best Regards,<br/>Your Team</p>" +

                        "</div>" +
                        "</body>" +
                        "</html>";

        return reportHtmlContent;
    }
}
