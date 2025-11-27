package com.example.notificationmanager.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SmsMessage {
    private String phone;
    private String text;
}

