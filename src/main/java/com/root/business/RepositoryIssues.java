package com.root.business;

import com.root.domain.Issue;
import com.root.domain.IssuesPage;
import com.root.http.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created on 23/05/2017.
 */
public class RepositoryIssues {


    final static String urlTemplate = "https://api.github.com/repos/%s/issues?per_page=100";

    public static CompletableFuture<List<Issue>> getRepoIssuesAsync(AsyncHttpClient asyncHttpClient, ObjectMapper mapper,
                                                                     List<Issue> issues, String repo){

            return CompletableFuture.supplyAsync(() -> {
                IssuesPage issuesPage;
                String url = String.format(urlTemplate,repo);

                do {
                    CompletableFuture<IssuesPage> future = HttpHandler.asyncGet(asyncHttpClient, mapper, url);
                    issuesPage = future.join();
                    issues.addAll(issuesPage.getIssues());
                    url = issuesPage.getNext();
                } while(url!=null);

                return issues;
            });

    }
}
