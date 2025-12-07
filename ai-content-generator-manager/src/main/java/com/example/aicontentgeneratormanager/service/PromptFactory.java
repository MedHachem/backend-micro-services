package com.example.aicontentgeneratormanager.service;

import com.example.aicontentgeneratormanager.dto.GenerateContentRequest;

public class PromptFactory {
    public static String buildPrompt(String filledTemplate, GenerateContentRequest request) {
        return """
           Rédige un contenu pour %s avec le ton %s et longueur %s :
           %s
           Important : retourne **un seul objet JSON valide** avec exactement ces champs :
           {
             "subject": "...",
             "bodyText": "...",
             "bodyHtml": "..."
           }
           - Échappe tous les guillemets et retours à la ligne
           - bodyText : version texte simple 
           - bodyHtml : version HTML 
           """.formatted(
                request.getContentType(),
                request.getTone(),
                request.getLength(),
                filledTemplate
        );
    }




}
