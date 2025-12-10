package com.example.notificationmanager.service.implementation;

import com.example.notificationmanager.model.GeneratedContentResponse;
import com.example.notificationmanager.service.SmsSenderService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderServiceImpl implements SmsSenderService {
    @Value("${app.sms.from}")
    private String from;
    @Value("${app.sms.account-sid}")
    private String accountSid;
    @Value("${app.sms.auth-token}")
    private String authToken;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }
    @Override
    public void sendSmsNotification(GeneratedContentResponse content) {
        System.out.println("🔹 SMS bodyText : " + content.getBodyText());

        Message.creator(
                new PhoneNumber("whatsapp:" + content.getRecipient()),
                new PhoneNumber(from),
                content.getBodyText()
        ).create();
    }
}
