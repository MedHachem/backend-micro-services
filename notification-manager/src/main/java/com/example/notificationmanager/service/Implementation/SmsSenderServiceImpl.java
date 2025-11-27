package com.example.notificationmanager.service.Implementation;

import com.example.notificationmanager.service.SmsSenderService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
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
    public void send(String to, String message) {
        Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber(from),
                message
        ).create();
    }
}
