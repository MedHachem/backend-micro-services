package com.islam.quranmanager.controller;

import com.islam.quranmanager.service.verseService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
@RestController
public class verseController {

    private final verseService verseService;

    public verseController(verseService verseService) {
        this.verseService = verseService;
    }

    @GetMapping("/byChapter/{chapterId}")
    public Mono<String> getVerseByChapter(@PathVariable Long chapterId) {
        return verseService.getVerseByChapter(chapterId);
    }
    @GetMapping("/indopak-script")
    public Mono<String> getIndopakScript(
            @RequestParam(required = false) Integer chapterNumber,
            @RequestParam(required = false) Integer juzNumber,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer hizbNumber,
            @RequestParam(required = false) Integer rubElHizbNumber,
            @RequestParam(required = false) String verseKey) {
        return verseService.getIndopakScript(chapterNumber, juzNumber, pageNumber, hizbNumber, rubElHizbNumber, verseKey);
    }
    @GetMapping("/play-recitation/{recitationId}")
    public Mono<String> playRecitation(@PathVariable Integer recitationId) {
          return   verseService.playRecitation(recitationId);
    }
}
