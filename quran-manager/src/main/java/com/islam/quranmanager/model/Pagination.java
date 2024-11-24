package com.islam.quranmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {
    @JsonProperty("per_page")
    private Integer perPage;

    @JsonProperty("current_page")
    private Integer currentPage;

    @JsonProperty("next_page")
    private Integer nextPage;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_records")
    private Integer totalRecords;
}
