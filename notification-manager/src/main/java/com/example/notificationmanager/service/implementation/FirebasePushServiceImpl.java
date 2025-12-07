package com.example.notificationmanager.service.implementation;

import com.example.notificationmanager.service.FirebasePushService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;


@Service
public class FirebasePushServiceImpl implements FirebasePushService {
    @Override
    public void sendPush(String fcmToken, String subject, String body) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(subject != null ? subject : "Notification")
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notification)
                    .build();

            FirebaseMessaging.getInstance().send(message);
            System.out.println("🔥 Push Firebase envoyée à " + fcmToken);
        } catch (FirebaseMessagingException e) {
            System.err.println("❌ Erreur lors de l’envoi du push Firebase : " + e.getMessage());
        }
    }
}
