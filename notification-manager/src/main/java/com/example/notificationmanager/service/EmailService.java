package com.example.notificationmanager.service;

public interface EmailService {
    void sendWelcomeEmail(String to, String name);
    void sendEmail(String to, String subject, String body);
}
