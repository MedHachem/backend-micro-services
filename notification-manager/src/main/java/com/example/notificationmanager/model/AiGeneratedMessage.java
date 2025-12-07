package com.example.notificationmanager.model;

import lombok.Data;

@Data
public class AiGeneratedMessage {
    private String recipient;
    private String subject;
    private String bodyText;
    private String bodyHtml;
    private String contentType; // "email" ou "sms"

    public String getContent() {
        // retourne bodyHtml si tu veux envoyer HTML, sinon bodyText
        return bodyHtml != null ? bodyHtml : bodyText;
    }
}

