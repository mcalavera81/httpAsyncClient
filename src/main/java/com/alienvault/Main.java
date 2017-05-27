package com.alienvault;

import com.alienvault.business.RepositoryIssues;
import com.alienvault.domain.Issue;
import com.alienvault.report.ReportPrinter;
import com.alienvault.util.FutureUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.asynchttpclient.AsyncHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.asynchttpclient.Dsl.asyncHttpClient;



public class Main {

    /**
     * For unauthenticated requests, the rate limit allows you to make up to 60 requests per hour.
     *
     */


    /**
     * @param args String array with Github repositories with the format "owner/repository"
     */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {



        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<CompletableFuture<List<Issue>>> completableFutures = new ArrayList<>();
        try(AsyncHttpClient asyncHttpClient = asyncHttpClient()){

            List<Issue> issues = new ArrayList<>();

            Arrays.stream(args).forEach(repo -> {
                CompletableFuture<List<Issue>> repoIssuesAsync = RepositoryIssues.getRepoIssuesAsync(asyncHttpClient, mapper, issues, repo);
                completableFutures.add(repoIssuesAsync);
            });

            FutureUtils.groupFutures(completableFutures).thenAccept(issuesList->
                    ReportPrinter.printIssuesReport(mapper, issuesList)
            ).join();

        }
    }

}
