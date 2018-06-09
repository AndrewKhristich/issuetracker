package com.axmor.service;

import com.axmor.model.Issue;

import java.util.List;

public interface IssueService {
    List<Issue> findAllIssues();
    List<Issue> findAllIssuesByName(String name);
    Issue findIssuesById(Long id);
    void saveIssue(Issue issue);
    List<Issue> findAuthentificatedUserIssue(String username);

}
