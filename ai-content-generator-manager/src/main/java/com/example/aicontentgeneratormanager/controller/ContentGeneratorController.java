package com.example.aicontentgeneratormanager.controller;

import com.example.aicontentgeneratormanager.dto.GenerateContentRequest;
import com.example.aicontentgeneratormanager.dto.GeneratedContentResponse;
import com.example.aicontentgeneratormanager.service.ContentGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai-content")
@RequiredArgsConstructor
public class ContentGeneratorController {
    private final ContentGeneratorService contentGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<GeneratedContentResponse> generate(@RequestBody GenerateContentRequest request) {
        return ResponseEntity.ok(contentGeneratorService.generateContent(request));
    }
}
