package com.alienvault.report;

import com.alienvault.domain.Issue;
import com.alienvault.domain.IssuesMostFrequentDay;
import com.alienvault.domain.Report;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created on 23/05/2017.
 */
public class ReportPrinter {

    private static List<Issue> sortIssues(List<Issue> issuesList) {
        return issuesList.stream().sorted(Comparator.comparing(Issue::getCreated_at)).collect(Collectors.toList());
    }

    public static void printIssuesReport(ObjectMapper mapper, List<Issue> issuesList) {

        List<Issue> sortedIssues = sortIssues(issuesList);


        Optional<Map.Entry<LocalDate, Map<String, Long>>> reportOpt = sortedIssues.stream().collect(
                Collectors.groupingBy(issue -> new java.sql.Date(issue.getCreated_at().getTime()).toLocalDate(),
                        Collectors.groupingBy(Issue::getRepository, Collectors.counting()))
        ).entrySet().stream().sorted(issuesComparator).findFirst();

        reportOpt.ifPresent(report-> {
            IssuesMostFrequentDay mostFrequentDay= new IssuesMostFrequentDay(report.getKey(), report.getValue());
            Report rep = new Report(sortedIssues,mostFrequentDay);
            System.out.println(rep.format(mapper));
        });
    }

    private static Comparator<Map.Entry<LocalDate, Map<String, Long>>> issuesComparator = (o1, o2) -> {

        int numberIssuesDif = (int) (o2.getValue().values().stream().mapToLong(i -> i).sum() - o1.getValue().values().stream().mapToLong(i -> i).sum());
        if (numberIssuesDif == 0) {
            return o2.getKey().compareTo(o1.getKey());
        } else {
            return numberIssuesDif;
        }

    };
}
