package com.example.aicontentgeneratormanager.dto;

import lombok.Data;

@Data
public class GeneratedContentResponse {
    private String subject;
    private String bodyText;
    private String bodyHtml;
    private String modelUsed;
    private int tokensUsed;
}

