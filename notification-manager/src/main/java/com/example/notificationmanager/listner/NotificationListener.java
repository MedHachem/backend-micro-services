package com.example.notificationmanager.listner;

import com.example.notificationmanager.model.AiGeneratedMessage;
import com.example.notificationmanager.service.EmailService;
import com.example.notificationmanager.service.SmsSenderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private final EmailService emailService ;
    private final SmsSenderService  smsSenderService;

    public NotificationListener(EmailService emailService, SmsSenderService smsSenderService) {
        this.emailService=emailService;
        this.smsSenderService=smsSenderService;
    }
    @RabbitListener(queues = "notification.generated", containerFactory = "rabbitListenerContainerFactory")
    public void handleGeneratedContent(AiGeneratedMessage message) {
        System.out.println("📩 Message AI reçu pour : " + message.getRecipient()+ ": " +message.getContent());
        System.out.println("📨 Contenu reçu LLM : " + message);

        try {
            if ("email".equalsIgnoreCase(message.getContentType())) {
                emailService.sendEmail(message.getRecipient(), message.getSubject(), message.getContent());
            } else if ("sms".equalsIgnoreCase(message.getContentType())) {
                smsSenderService.send(message.getRecipient(), message.getContent());
            }
        } catch (Exception e) {
            System.err.println("⚠️ Échec de l'envoi pour " + message.getRecipient() + ": " + e.getMessage());
            // éventuellement loguer l'erreur ou envoyer dans une DLQ
        }
    }



}
