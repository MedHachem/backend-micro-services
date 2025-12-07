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

        // 1. Charger template selon type de contenu
        String rawTemplate = templateService.loadTemplate(request.getTemplateId(), request.getContentType());

        // 2. Remplacer variables
        String filledTemplate = templateService.applyVariables(rawTemplate, request.getVariables());

        // 3. Construire le prompt
        String prompt = PromptFactory.buildPrompt(filledTemplate, request);

        // 4. Appel LLM
        GeneratedContentResponse response = llmService.generateContent(prompt);

        // 5. Envoi direct via RabbitMQ si demandé
        if (request.isSendDirectly()) {
            rabbitProducer.sendContent(response, request.getRecipient(), request.getContentType());
        }

        return response;
    }
}
