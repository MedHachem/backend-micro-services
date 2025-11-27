package com.example.notificationmanager.service.Implementation;

import com.example.notificationmanager.service.NotificationService;
import com.example.notificationmanager.model.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendWelcomeEmail(User user) {
        System.out.println("Email envoyé à : " + user.getEmail());
    }
}
