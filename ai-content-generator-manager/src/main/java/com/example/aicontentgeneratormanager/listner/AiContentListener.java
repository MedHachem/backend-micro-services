package com.example.aicontentgeneratormanager.listner;

import com.example.aicontentgeneratormanager.dto.GenerateContentRequest;
import com.example.aicontentgeneratormanager.model.User;
import com.example.aicontentgeneratormanager.service.ContentGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Component

public class AiContentListener {
    private final ContentGeneratorService contentGeneratorService;
    private static final Logger logger = LoggerFactory.getLogger(AiContentListener.class);

    public AiContentListener(ContentGeneratorService contentGeneratorService) {
        this.contentGeneratorService = contentGeneratorService;
    }
    @RabbitListener(queues = "user.notifications", containerFactory = "rabbitListenerContainerFactory")
    public void handleUserRegistered(User user) {
        logger.info("Message reçu dans AiContentListener : {} - {}", user.getFirstname(), user.getEmail());

        // ------------------- EMAIL ------------------- //
        GenerateContentRequest emailRequest = new GenerateContentRequest();
        emailRequest.setTemplateId("welcome_email"); // template email
        emailRequest.setContentType("email");
        emailRequest.setVariables(Map.of(
                "user_name", user.getFirstname(),
                "user_email", user.getEmail()
        ));
        emailRequest.setRecipient(user.getEmail());
        emailRequest.setSendDirectly(true);

        try {
            contentGeneratorService.generateContent(emailRequest);
            logger.info("✅ Contenu email généré et envoyé à {}", user.getEmail());
        } catch (WebClientResponseException.TooManyRequests e) {
            logger.warn("OpenAI rate limit atteint pour l'utilisateur {}. Email sera réessayé plus tard.", user, e);
            throw e;
        } catch (Exception e) {
            logger.error("Erreur lors de la génération du contenu email pour {}", user, e);
        }

        // ------------------- SMS ------------------- //
        String smsRecipient = "+21629235322"; // remplacer par user.getPhone() si disponible
        logger.info("Préparation du SMS pour : {} - {}", user.getFirstname(), smsRecipient);

        GenerateContentRequest smsRequest = new GenerateContentRequest();
        smsRequest.setTemplateId("welcome_sms"); // template SMS
        smsRequest.setContentType("sms");
        smsRequest.setVariables(Map.of(
                "user_name", user.getFirstname()
        ));
        smsRequest.setRecipient(smsRecipient);
        smsRequest.setSendDirectly(true);

        try {
            contentGeneratorService.generateContent(smsRequest);
            logger.info("✅ Contenu SMS généré et envoyé à {}", smsRecipient);
        } catch (WebClientResponseException.TooManyRequests e) {
            logger.warn("OpenAI rate limit atteint pour l'utilisateur {}. SMS sera réessayé plus tard.", user, e);
            throw e;
        } catch (Exception e) {
            logger.error("Erreur lors de la génération du contenu SMS pour {}", user, e);
        }
// ------------------- FIREBASE PUSH NOTIFICATION ------------------- //

        String firebaseToken = "jjjjjjjjjjjj"; // S’il existe, sinon à ajouter dans User

        logger.info("Préparation de la Push Notification Firebase pour {} - token : {}",
                user.getFirstname(), firebaseToken);

        GenerateContentRequest pushRequest = new GenerateContentRequest();
        pushRequest.setTemplateId("welcome_push");
        pushRequest.setContentType("push_notification");
        pushRequest.setVariables(Map.of(
                "user_name", user.getFirstname()
        ));
        pushRequest.setRecipient(firebaseToken); // tu enverras le token FCM ici
        pushRequest.setSendDirectly(true);

        try {
            contentGeneratorService.generateContent(pushRequest);
            logger.info("🔥 Push Notification générée et envoyée via Firebase pour {}", user.getFirstname());
        } catch (WebClientResponseException.TooManyRequests e) {
            logger.warn("OpenAI rate limit atteint. Push sera réessayée plus tard. User: {}", user, e);
            throw e;
        } catch (Exception e) {
            logger.error("Erreur lors de la génération du contenu PUSH pour {}", user, e);
        }


        try {
            contentGeneratorService.generateContent(smsRequest);
            logger.info("✅ Contenu SMS généré et envoyé à {}", smsRecipient);
        } catch (WebClientResponseException.TooManyRequests e) {
            logger.warn("OpenAI rate limit atteint pour l'utilisateur {}. SMS sera réessayé plus tard.", user, e);
            throw e;
        } catch (Exception e) {
            logger.error("Erreur lors de la génération du contenu SMS pour {}", user, e);
        }
    }


}
