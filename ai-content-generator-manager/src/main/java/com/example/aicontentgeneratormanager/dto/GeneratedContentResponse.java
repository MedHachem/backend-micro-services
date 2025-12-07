package com.example.aicontentgeneratormanager.dto;

import lombok.Data;

@Data
public class GeneratedContentResponse {
    private String subject;     //For emails
    private String bodyText;
    private String bodyHtml;    // optional
    private String modelUsed;
    private int tokensUsed;
}

