package com.alienvault.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created on 23/05/2017.
 */
@Getter
public class IssuesMostFrequentDay {

    public IssuesMostFrequentDay(LocalDate day, Map<String, Long> ocurrences) {
        this.day = day;
        this.ocurrences = ocurrences;
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate day;

    @JsonProperty
    private Map<String,Long> ocurrences;

}
