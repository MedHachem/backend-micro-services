package com.example.notificationmanager.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
