package com.example.aicontentgeneratormanager.dto;

import lombok.Data;

import java.util.Map;

@Data
public class GenerateContentRequest {
    private String templateId;
    private String contentType;
    private String tone;
    private String length;
    private String recipient;
    private String eventType;
    private String userId;
    private String email;
    private String phone;
    private String fcmToken;
    private Map<String, Object> data;

    public String getTemplateId() {
        if (eventType != null && contentType != null) {
            return eventType.toLowerCase() + "_" + contentType.toLowerCase();
        }
        return templateId;
    }
    public String getRecipientForContentType() {
        return switch (contentType.toLowerCase()) {
            case "email" -> email;
            case "sms" -> phone;
            case "push_notification" -> fcmToken;
            default -> null;
        };
    }

}

