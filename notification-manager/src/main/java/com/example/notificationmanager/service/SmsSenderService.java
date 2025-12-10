package com.example.notificationmanager.service;

import com.example.notificationmanager.model.GeneratedContentResponse;

public interface SmsSenderService {
    void sendSmsNotification(GeneratedContentResponse content);

}
