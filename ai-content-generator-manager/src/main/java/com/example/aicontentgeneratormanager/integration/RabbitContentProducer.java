package com.example.aicontentgeneratormanager.integration;

import com.example.aicontentgeneratormanager.dto.ContentSendMessage;
import com.example.aicontentgeneratormanager.dto.GeneratedContentResponse;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitContentProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "generatedExchange";       // doit matcher NotificationListener
    private static final String ROUTING_KEY = "generated.content";    // idem

    public void sendContent(GeneratedContentResponse content, String recipient, String contentType) {
        String payload;

        if ("sms".equalsIgnoreCase(contentType)) {
            // SMS : on utilise bodyText. S'il est null, fallback en convertissant le HTML en texte brut
            payload = content.getBodyText();
            if (payload == null || payload.isBlank()) {
                // Utilise Jsoup pour retirer les balises HTML
                payload = Jsoup.parse(content.getBodyHtml()).text();
            }
        } else {
            // Email : on utilise bodyHtml
            payload = content.getBodyHtml();
        }

        ContentSendMessage msg = new ContentSendMessage(contentType, content.getSubject(), payload, recipient);
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);
        System.out.println("Message envoyé à RabbitMQ : " + contentType + " -> " + recipient);
    }

}

