package com.example.notificationmanager.service.Implementation;

import com.example.notificationmanager.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Bienvenue sur ProKick !");
        message.setText("Salut " + name + ",\n\nMerci de vous être inscrit !\n\nCordialement,\nL'équipe ProKick");

        mailSender.send(message);
        System.out.println("Email envoyé à : " + to);
    }
    public void sendEmail(String to, String subject, String bodyHtml) {

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(bodyHtml, true); // true = c'est du HTML

            mailSender.send(message);
            System.out.println("✅ Email dynamique envoyé à : " + to);
        } catch (MessagingException e) {
            System.err.println("⚠️ Échec de l'envoi pour " + to + ": " + e.getMessage());
        }
    }
}

