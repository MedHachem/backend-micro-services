package com.example.aicontentgeneratormanager.service;

import com.example.aicontentgeneratormanager.dto.GenerateContentRequest;
import com.example.aicontentgeneratormanager.dto.GeneratedContentResponse;
import com.example.aicontentgeneratormanager.integration.RabbitContentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentGeneratorService {

    private final TemplateService templateService;
    private final LLMService llmService;
    private final RabbitContentProducer rabbitProducer;

    public GeneratedContentResponse generateContent(GenerateContentRequest request) {

        String rawTemplate = templateService.loadTemplate(request.getTemplateId(), request.getContentType());

        String filledTemplate = templateService.applyVariables(rawTemplate, request.getVariables());

        String prompt = PromptFactory.buildPrompt(filledTemplate, request);

        GeneratedContentResponse response = llmService.generateContent(prompt);

        if (request.isSendDirectly()) {
            rabbitProducer.sendContent(response, request.getRecipient(), request.getContentType());
        }

        return response;
    }
}
