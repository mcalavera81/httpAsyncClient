package com.alienvault.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Created on 23/05/2017.
 */
@Getter
@RequiredArgsConstructor
public class IssuesPage {

    private final List<Issue> issues;

    private final String next;
}
