package com.islam.quranmanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class verse {
    private Long id;
    private Integer verseNumber;
    private String verseKey;
    private Integer hizbNumber;
    private Integer rubElHizbNumber;
    private Integer rukuNumber;
    private Integer manzilNumber;
    private Integer sajdahNumber;
    private Integer pageNumber;
    private Integer juzNumber;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVerseNumber() {
        return verseNumber;
    }

    public void setVerseNumber(Integer verseNumber) {
        this.verseNumber = verseNumber;
    }

    public String getVerseKey() {
        return verseKey;
    }

    public void setVerseKey(String verseKey) {
        this.verseKey = verseKey;
    }

    public Integer getHizbNumber() {
        return hizbNumber;
    }

    public void setHizbNumber(Integer hizbNumber) {
        this.hizbNumber = hizbNumber;
    }

    public Integer getRubElHizbNumber() {
        return rubElHizbNumber;
    }

    public void setRubElHizbNumber(Integer rubElHizbNumber) {
        this.rubElHizbNumber = rubElHizbNumber;
    }

    public Integer getRukuNumber() {
        return rukuNumber;
    }

    public void setRukuNumber(Integer rukuNumber) {
        this.rukuNumber = rukuNumber;
    }

    public Integer getManzilNumber() {
        return manzilNumber;
    }

    public void setManzilNumber(Integer manzilNumber) {
        this.manzilNumber = manzilNumber;
    }

    public Integer getSajdahNumber() {
        return sajdahNumber;
    }

    public void setSajdahNumber(Integer sajdahNumber) {
        this.sajdahNumber = sajdahNumber;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getJuzNumber() {
        return juzNumber;
    }

    public void setJuzNumber(Integer juzNumber) {
        this.juzNumber = juzNumber;
    }
}
