package com.root.util;

import com.root.domain.Issue;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created on 23/05/2017.
 */
public class FutureUtils {

    public static CompletableFuture<List<Issue>> groupFutures(List<CompletableFuture<List<Issue>>> futures){
        CompletableFuture<Void> allDoneFuture =CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        CompletableFuture<List<Issue>> futureIssuesList = allDoneFuture.thenApply(v ->
                futures.stream().flatMap(future ->  future.join().stream()).collect(Collectors.toList())
        );
        return  futureIssuesList;
    }

}
