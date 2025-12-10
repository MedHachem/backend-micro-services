package com.example.usermanager.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
@Data
@Builder
public class NotificationRequestDTO {
    private String eventType;
    private String contentType;
    private String userId;             // cible
    private String email;              // optionnel
    private String phone;              // optionnel
    private String fcmToken;           // optionnel (pour push)
    private Map<String, Object> data;  // données dynamiques (variables template)
}