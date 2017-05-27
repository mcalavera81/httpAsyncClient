package com.root.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.List;

/**
 * Created on 22/05/2017.
 */
@Getter
public class Report {

    public Report(List<Issue> issues, IssuesMostFrequentDay top_day) {
        this.issues = issues;
        this.top_day = top_day;
    }

    private List<Issue> issues;

    private IssuesMostFrequentDay top_day;

    public String format(ObjectMapper objectMapper){
        String s = null;
        try {
            s = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

}
