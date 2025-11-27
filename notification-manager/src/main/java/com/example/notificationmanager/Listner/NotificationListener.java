package com.example.notificationmanager.Listner;

import com.example.notificationmanager.Service.EmailService;
import com.example.notificationmanager.Service.NotificationService;
import com.example.notificationmanager.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private final NotificationService notificationService;
    private final EmailService emailService ;

    public NotificationListener(NotificationService notificationService,EmailService emailService) {
        this.notificationService = notificationService;
            this.emailService=emailService;
    }
    @RabbitListener(queues = "user.notifications", containerFactory = "rabbitListenerContainerFactory")
    public void handleUserRegistered(User user) {
        System.out.println("Nouvel utilisateur : " + user.getEmail());
        emailService.sendWelcomeEmail(user.getEmail(), user.getFirstname());
    }

}
