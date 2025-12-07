package com.example.aicontentgeneratormanager.dto;

import lombok.Data;

import java.util.Map;

@Data
public class GenerateContentRequest {
    private String templateId;           // (email, sms, post)
    private Map<String, Object> variables;
    private String contentType;          // "email", "sms", "post" ...
    private String tone;                 // "professional", "friendly", "urgent"
    private String length;               // "short", "medium", "long"
    private boolean sendDirectly;        // true = push via notification-manager
    private String recipient;            // email or number
}

