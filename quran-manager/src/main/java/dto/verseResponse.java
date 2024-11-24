package dto;

import com.islam.quranmanager.model.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class verseResponse {
    private List<verseDTO> verses;
    private Pagination pagination;
    // Getters et Setters
    public List<verseDTO> getVerses() {
        return verses;
    }

    public void setVerses(List<verseDTO> verses) {
        this.verses = verses;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
