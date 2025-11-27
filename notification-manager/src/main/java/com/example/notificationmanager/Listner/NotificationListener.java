package com.example.notificationmanager.Listner;

import com.example.notificationmanager.service.EmailService;
import com.example.notificationmanager.service.NotificationService;
import com.example.notificationmanager.service.SmsSenderService;
import com.example.notificationmanager.model.SmsMessage;
import com.example.notificationmanager.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private final NotificationService notificationService;
    private final EmailService emailService ;
    private final SmsSenderService  smsSenderService;

    public NotificationListener(NotificationService notificationService, EmailService emailService, SmsSenderService smsSenderService) {
        this.notificationService = notificationService;
            this.emailService=emailService;
        this.smsSenderService=smsSenderService;
    }
    @RabbitListener(queues = "user.notifications", containerFactory = "rabbitListenerContainerFactory")
    public void handleUserRegistered(User user) {
        System.out.println("Nouvel utilisateur : " + user.getEmail());
        emailService.sendWelcomeEmail(user.getEmail(), user.getFirstname());
    }
    @RabbitListener(queues = "sms-queue", containerFactory = "rabbitListenerContainerFactory")
    public void consume(SmsMessage sms) {
        System.out.println("📩 Message SMS reçu : " + sms.getPhone());
        smsSenderService.send(sms.getPhone(), sms.getText());
    }


}
