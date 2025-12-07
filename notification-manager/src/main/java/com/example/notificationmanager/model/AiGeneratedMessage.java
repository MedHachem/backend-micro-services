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
        return bodyHtml != null ? bodyHtml : bodyText;
    }
}

