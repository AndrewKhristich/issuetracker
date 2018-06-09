package com.axmor.dao;

import com.axmor.model.Issue;

import java.util.List;

public interface IssueDao {

    List<Issue> findAllIssues();
    List<Issue> findAuthentificatedUserIssues(String username);
    List<Issue> findIssuesByName(String issueName);
    Issue findIssuesById(Long id);
    void saveIssues(Issue issue);
}
