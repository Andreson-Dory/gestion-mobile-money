package com.mobilemoney.service;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {

    // private final String username = "narindrarasoavololona@gmail.com";
    // private final String password = "adminN";
	private final String username = "sedraniainadory@gmail.com";
    private final String password = "tkpi qefr kqln qphx";
   

    public void sendEmail(String toEmail, String subject, String messageText) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail)
            );

            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);

            System.out.println("Email envoyé avec succès");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}