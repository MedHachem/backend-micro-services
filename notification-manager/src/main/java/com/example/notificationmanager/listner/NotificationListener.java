package com.example.notificationmanager.listner;

import com.example.notificationmanager.model.GeneratedContentResponse;
import com.example.notificationmanager.service.EmailService;
import com.example.notificationmanager.service.FirebasePushService;
import com.example.notificationmanager.service.SmsSenderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private final EmailService emailService ;
    private final SmsSenderService  smsSenderService;
    private final FirebasePushService firebasePushService;
    public NotificationListener(EmailService emailService, SmsSenderService smsSenderService, FirebasePushService firebasePushService) {
        this.emailService=emailService;
        this.smsSenderService=smsSenderService;
        this.firebasePushService=firebasePushService;
    }
    @RabbitListener(queues = "notification.generated", containerFactory = "rabbitListenerContainerFactory")
    public void handleGeneratedContent(GeneratedContentResponse message) {
        try {
            switch (message.getContentType().toLowerCase()) {

                case "email":
                    emailService.sendEmailNotification(message);
                    break;

                case "sms":
                    smsSenderService.sendSmsNotification(message);
                    break;

                case "push":
                case "push_notification":
                    firebasePushService.sendPush(
                            message.getRecipient(),   
                            message.getSubject(),
                            message.getBodyText()
                    );
                    break;

                default:
                    System.err.println("❌ Type de notification inconnu : " + message.getContentType());
            }

        } catch (Exception e) {
            System.err.println("⚠️ Échec envoi notification : " + e.getMessage());
            // possibilité de DLQ
        }
    }



}
