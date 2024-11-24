package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class verseDTO {
    private Long id;

    @JsonProperty("verse_number")
    private Integer verseNumber;

    @JsonProperty("verse_key")
    private String verseKey;

    @JsonProperty("hizb_number")
    private Integer hizbNumber;

    @JsonProperty("rub_el_hizb_number")
    private Integer rubElHizbNumber;

    @JsonProperty("ruku_number")
    private Integer rukuNumber;

    @JsonProperty("manzil_number")
    private Integer manzilNumber;

    @JsonProperty("sajdah_number")
    private Integer sajdahNumber;

    @JsonProperty("page_number")
    private Integer pageNumber;

    @JsonProperty("juz_number")
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
