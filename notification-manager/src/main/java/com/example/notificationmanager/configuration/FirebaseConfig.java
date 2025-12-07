package com.example.notificationmanager.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Value("${FIREBASE_PROJECT_ID}")
    private String projectId;

    @Value("${FIREBASE_CLIENT_EMAIL}")
    private String clientEmail;

    @Value("${FIREBASE_PRIVATE_KEY}")
    private String privateKey;

    @PostConstruct
    public void init() throws IOException {

        privateKey = privateKey.replace("\\n", "\n");

        String json = "{"
                + "\"type\": \"service_account\","
                + "\"project_id\": \"" + projectId + "\","
                + "\"private_key_id\": \"dummy\","
                + "\"private_key\": \"" + privateKey + "\","
                + "\"client_email\": \"" + clientEmail + "\","
                + "\"client_id\": \"dummy\","
                + "\"token_uri\": \"https://oauth2.googleapis.com/token\""
                + "}";

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new ByteArrayInputStream(json.getBytes()));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
