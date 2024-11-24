package com.islam.quranmanager.service.serviceImpl;

import com.islam.quranmanager.helper.AudioStreamPlayer;
import com.islam.quranmanager.model.verse;
import com.islam.quranmanager.service.verseService;
import dto.verseDTO;
import dto.verseResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.islam.quranmanager.mapper.verseMapper;
import reactor.core.publisher.Mono;

@Service
public class verseServiceImpl implements verseService {
    private final WebClient.Builder webClientBuilder;
    private final OkHttpClient client;
    private final String baseUrl;

    public verseServiceImpl(@Value("${api.quran.base-url}") String baseUrl, WebClient.Builder webClientBuilder) {
        this.baseUrl = baseUrl;
        this.webClientBuilder = webClientBuilder;
        this.client = new OkHttpClient();
    }

    @Override
    public Mono<String> getVerseByChapter(Long chapterId) {
        return webClientBuilder.baseUrl(baseUrl).build().get()
                .uri(uriBuilder -> uriBuilder.path("verses/by_chapter/{chapterId}").build(chapterId))
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> getIndopakScript(Integer chapterNumber, Integer juzNumber, Integer pageNumber,
                                         Integer hizbNumber, Integer rubElHizbNumber, String verseKey) {
        return webClientBuilder.baseUrl(baseUrl).build().get()
                .uri(uriBuilder -> uriBuilder.path("quran/verses/indopak_script")
                        .queryParam("chapter_number", chapterNumber != null ? chapterNumber : "")
                        .queryParam("juz_number", juzNumber != null ? juzNumber : "")
                        .queryParam("page_number", pageNumber != null ? pageNumber : "")
                        .queryParam("hizb_number", hizbNumber != null ? hizbNumber : "")
                        .queryParam("rub_el_hizb_number", rubElHizbNumber != null ? rubElHizbNumber : "")
                        .queryParam("verse_key", verseKey != null ? verseKey : "")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> playRecitation(Integer recitationId) {
       return webClientBuilder.baseUrl(baseUrl).build().get()
                .uri(uriBuilder -> uriBuilder.path("chapter_recitations/"+ recitationId)
                        .build()).retrieve().bodyToMono(String.class);
    }

}
