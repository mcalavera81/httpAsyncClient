package com.alienvault.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 22/05/2017.
 * * {
 * "id": 1,
 * "state": "open",
 * "title": "Found a bug",
 * "repository": "owner1/repository1",
 * "created_at": "2011-04-22T13:33:48Z"
 * }
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
public class Issue {

    private final static Pattern pattern = Pattern.compile("^.*/([^/]+/.*)$");

    private Integer id;

    private String state;

    private String title;

    private String repository;

    @JsonProperty("repository_url")
    public void setRepository(String repository) {
        Matcher matcher = pattern.matcher(repository);
        if(matcher.find()){
            this.repository = matcher.group(1);
        }
    }


    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="GMT")
    private Date created_at;


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
