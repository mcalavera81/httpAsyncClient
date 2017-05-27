package com.alienvault.http;

import com.alienvault.domain.IssuesPage;
import com.alienvault.parser.ResponseParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;

import java.util.concurrent.CompletableFuture;

/**
 * Created on 23/05/2017.
 */
public class HttpHandler {

    public static CompletableFuture<IssuesPage> asyncGet(AsyncHttpClient httpClient, ObjectMapper mapper, String url){
        return httpClient
                .prepareGet(url)
                .execute()
                .toCompletableFuture()
                .exceptionally(t -> {
                    t.printStackTrace();
                    return  null;
                })
                .thenApply(ResponseParser.httpResponseToIssuesList(mapper));
    }
}
