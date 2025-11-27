package com.example.notificationmanager.Service.Implementation;

import com.example.notificationmanager.Service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
}

