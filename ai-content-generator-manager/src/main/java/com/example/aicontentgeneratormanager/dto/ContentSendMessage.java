package com.example.aicontentgeneratormanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentSendMessage {
    private String contentType;
    private String subject;
    private String bodyHtml;
    private String recipient;
}