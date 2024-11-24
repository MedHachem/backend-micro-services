package com.islam.quranmanager.service;

import com.islam.quranmanager.model.verse;
import dto.verseDTO;
import dto.verseResponse;
import reactor.core.publisher.Mono;

public interface verseService {
    Mono<String> getVerseByChapter(Long chapterId);
    Mono<String> getIndopakScript(Integer chapterNumber, Integer juzNumber, Integer pageNumber,
                                  Integer hizbNumber, Integer rubElHizbNumber, String verseKey);

    Mono<String> playRecitation(Integer recitationId);
}
