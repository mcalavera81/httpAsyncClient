package com.alienvault.parser;

import com.alienvault.domain.Issue;
import com.alienvault.domain.IssuesPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created on 23/05/2017.
 */
public class ResponseParser {

    public static Function<Response, IssuesPage> httpResponseToIssuesList(ObjectMapper mapper) {
        return resp -> {

            List<Issue> issueResults = new ArrayList<>();
            String nextUrl=null;
            if(resp!=null) {
                try {
                    if(resp.getHeader("Link")!=null){
                        for (String link : resp.getHeader("Link").split(",")) {
                            String[] split = link.split(";");
                            if(split[1].contains("next")) {
                                nextUrl = split[0].replaceAll("<|>","");
                            }
                        }
                    }


                    issueResults = mapper.readValue(resp.getResponseBody(), new TypeReference<List<Issue>>() {
                    });

                    issueResults.stream();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return new IssuesPage(issueResults,nextUrl);
        };
    }
}
