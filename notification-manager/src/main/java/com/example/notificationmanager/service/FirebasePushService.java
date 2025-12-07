package com.example.notificationmanager.service;


public interface FirebasePushService {
     void sendPush(String fcmToken, String subject, String body);
}
