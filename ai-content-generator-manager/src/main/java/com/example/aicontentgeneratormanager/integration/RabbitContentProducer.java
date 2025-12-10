package com.example.aicontentgeneratormanager.integration;

import com.example.aicontentgeneratormanager.dto.ContentSendMessage;
import com.example.aicontentgeneratormanager.dto.GeneratedContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitContentProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "generatedExchange";
    private static final String ROUTING_KEY = "generated.content";  // idem

    public void sendContent(GeneratedContentResponse content, String recipient, String contentType) {
        ContentSendMessage msg = new ContentSendMessage(contentType, content.getSubject(), content.getBodyHtml(),content.getBodyText(), recipient);
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);
        System.out.println("Message envoyé à RabbitMQ : " + contentType + " -> " + recipient);
    }

}

