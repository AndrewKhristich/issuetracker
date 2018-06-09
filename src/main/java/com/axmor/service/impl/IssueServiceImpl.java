package com.axmor.service.impl;

import com.axmor.dao.IssueDao;
import com.axmor.exception.IssueNotFoundException;
import com.axmor.model.Issue;
import com.axmor.service.IssueService;

import java.util.List;

public class IssueServiceImpl implements IssueService {

    private IssueDao issueDao;

    public IssueServiceImpl(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    @Override
    public List<Issue> findAllIssues() {
        return issueDao.findAllIssues();
    }

    @Override
    public List<Issue> findAllIssuesByName(String name) {
        return issueDao.findIssuesByName(name);
    }

    @Override
    public Issue findIssuesById(Long id) {
        Issue issueById = issueDao.findIssuesById(id);
        if (issueById ==null){
            throw new IssueNotFoundException("Issue does not exist");
        }
        return issueById;
    }

    @Override
    public void saveIssue(Issue issue) {
        issueDao.saveIssues(issue);
    }

    @Override
    public List<Issue> findAuthentificatedUserIssue(String username) {
        return issueDao.findAuthentificatedUserIssues(username);
    }
}
