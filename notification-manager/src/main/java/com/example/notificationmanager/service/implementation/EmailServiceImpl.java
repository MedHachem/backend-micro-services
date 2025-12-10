package com.example.notificationmanager.service.implementation;

import com.example.notificationmanager.model.GeneratedContentResponse;
import com.example.notificationmanager.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailNotification(GeneratedContentResponse content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(content.getRecipient());
            helper.setSubject(content.getSubject());
            helper.setText(content.getBodyHtml(), true);

            mailSender.send(message);

            System.out.println("📧 Email envoyé à : " + content.getRecipient());

        } catch (MessagingException e) {
            System.err.println("⚠️ Erreur envoi email à " + content.getRecipient() + ": " + e.getMessage());
        }
    }

}

