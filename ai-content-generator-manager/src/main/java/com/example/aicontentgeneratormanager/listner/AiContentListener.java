package com.example.aicontentgeneratormanager.listner;

import com.example.aicontentgeneratormanager.dto.GenerateContentRequest;
import com.example.aicontentgeneratormanager.service.ContentGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



@Component

public class AiContentListener {
    private final ContentGeneratorService contentGeneratorService;
    private static final Logger logger = LoggerFactory.getLogger(AiContentListener.class);

    public AiContentListener(ContentGeneratorService contentGeneratorService) {
        this.contentGeneratorService = contentGeneratorService;
    }
    @RabbitListener(queues = "user.notifications", containerFactory = "rabbitListenerContainerFactory")
    public void handleNotificationRequest(GenerateContentRequest request) {
        try {
            contentGeneratorService.generateContent(request);
        } catch (Exception e) {
            logger.error("❌ Erreur génération contenu AI pour eventType={} : {}",
                    request.getEventType(), e.getMessage());
            // Optionnel: envoyer en DLQ
        }
    }


}
