package com.example.aicontentgeneratormanager.service;

import com.example.aicontentgeneratormanager.dto.GeneratedContentResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class LLMService {

    private final WebClient webClient;
    @Value("${LLM.HOST}")
    private String llmHost;

    @Value("${LLM.PORT}")
    private String llmPort;
    public GeneratedContentResponse generateContent(String prompt) {
        try {
            String payload = """
        {
          "model": "phi3",
          "messages": [
            { "role": "system", "content": "You are an expert content generator. Return JSON." },
            { "role": "user", "content": "%s" }
          ]
        }
        """.formatted(escapeJson(prompt));

            StringBuilder contentBuilder = new StringBuilder();
            String baseUrl = String.format("http://%s:%s/api/chat",llmHost, llmPort);
            webClient.post()
                    .uri(baseUrl)
                    .header("Content-Type", "application/json")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToFlux(JsonNode.class)
                    .toIterable()
                    .forEach(node -> {
                        JsonNode messageNode = node.get("message");
                        if (messageNode != null && messageNode.has("content")) {
                            contentBuilder.append(messageNode.get("content").asText());
                        }
                    });

            String raw = contentBuilder.toString().trim();

            if (raw.startsWith("```json")) {
                raw = raw.substring(7).trim();
            } else if (raw.startsWith("```")) {
                raw = raw.substring(3).trim();
            }

            if (raw.endsWith("```")) {
                raw = raw.substring(0, raw.length() - 3).trim();
            }
            System.out.println("✅ Contenu LLM final : " + raw);
            if (!raw.startsWith("{") || !raw.endsWith("}")) {
                throw new RuntimeException("LLM returned incomplete JSON: " + raw);
            }

            return new ObjectMapper().readValue(raw, GeneratedContentResponse.class);


        } catch (Exception e) {
            throw new RuntimeException("LLM call failed", e);
        }

    }

    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }
}
