package com.islam.quranmanager.mapper;

import com.islam.quranmanager.model.verse;
import dto.verseDTO;
import org.springframework.stereotype.Component;

@Component
public class VMapper {
    // Méthode pour mapper VerseDTO à Verse
    public verse toVerse(verseDTO verseDTO) {
        if (verseDTO == null) {
            return null;
        }

        verse verse = new verse();
        verse.setId(verseDTO.getId());
        verse.setVerseNumber(verseDTO.getVerseNumber());
        verse.setVerseKey(verseDTO.getVerseKey());
        verse.setHizbNumber(verseDTO.getHizbNumber());
        verse.setRubElHizbNumber(verseDTO.getHizbNumber());
        verse.setRukuNumber(verseDTO.getRukuNumber());
        verse.setManzilNumber(verseDTO.getManzilNumber());
        verse.setSajdahNumber(verseDTO.getSajdahNumber());
        verse.setPageNumber(verseDTO.getPageNumber());
        verse.setJuzNumber(verseDTO.getJuzNumber());

        return verse;
    }
}
