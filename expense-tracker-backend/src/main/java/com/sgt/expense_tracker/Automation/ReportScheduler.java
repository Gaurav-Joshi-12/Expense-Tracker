package com.sgt.expense_tracker.Automation;

import com.sgt.expense_tracker.Model.Transaction;
import com.sgt.expense_tracker.Model.User;
import com.sgt.expense_tracker.Repository.AuthRepository;
import com.sgt.expense_tracker.Service.EmailService;
import com.sgt.expense_tracker.Service.TransactionService;
import jakarta.mail.MessagingException;
import org.openpdf.text.Document;
import org.openpdf.text.Image;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Documented;
import java.time.LocalDate;
import java.util.List;

@Component
public class ReportScheduler {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    EmailService emailService;



    @Scheduled(cron = "0 0 0 * * SUN")
    public void sendReport() throws MessagingException {
        System.out.println("Sending report...");
        List<String> emails = authRepository.getAllUserEmails();
        List <User> usersList = authRepository.findAll();
        for(User u: usersList){
           byte[] report = getReport(u);
           emailService.sendWeeklyReportEmail(u.getEmail(),report);
        }
    }

    public byte[] getReport(User u) throws MessagingException {
        System.out.println("Sending report to users who's email is "+u.getEmail());
        List<Transaction> transactionList = transactionService.getTransaction(u.getUserId(),null,null,null,null,null,"ASC",10,1);
        for(Transaction t : transactionList){
            System.out.println(t);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document,out);
        document.open();
        document.add(new Paragraph("Transaction Report"));
        document.add(new Paragraph("Generated on: "+ LocalDate.now()));
        Image img = null;
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.addCell("Date");
        table.addCell("Category");
        table.addCell("Amount");

        for(Transaction t: transactionList){
            table.addCell(t.getDateOfTransaction().toString());
            table.addCell(t.getCategoryName());
            table.addCell(String.valueOf(t.getAmount()));
        }

        document.add(table);
        document.close();

        return out.toByteArray();



    }
}
