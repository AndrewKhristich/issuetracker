package com.axmor.dao.impl;

import com.axmor.dao.IssueDao;
import com.axmor.model.Issue;
import com.axmor.utils.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.axmor.utils.CustomMapper.mapIssueFromResultSet;

public class IssueDaoImpl implements IssueDao {

    private static final Logger LOG = LoggerFactory.getLogger(IssueDaoImpl.class);

    @Override
    public List<Issue> findAllIssues() {
        List<Issue> issues = new ArrayList<>();
        String query = "SELECT * FROM ISSUE";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                issues.add(mapIssueFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("FINDING ALL ISSUES FAIL" ,e);
        }
        return issues;
    }

    @Override
    public List<Issue> findAuthentificatedUserIssues(String username) {
        List<Issue> issues = new ArrayList<>();
        String query = "SELECT * FROM ISSUE WHERE AUTHOR = '" + username + "'";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                issues.add(mapIssueFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("FINDING USER ISSUES FAIL" ,e);
        }
        return issues;
    }

    @Override
    public List<Issue> findIssuesByName(String issueName) {
        List<Issue> issues = new ArrayList<>();
        String query = "SELECT * FROM ISSUE WHERE ISSUE.ISSUE_NAME LIKE '%" + issueName + "%'";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                issues.add(mapIssueFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("FINDING ISSUE BY NAME FAIL" ,e);
        }
        return issues;
    }

    @Override
    public Issue findIssuesById(Long id) {
        List<Issue> issues = new ArrayList<>();
        String query = "SELECT * FROM ISSUE WHERE ID = " + id;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                issues.add(mapIssueFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("FINDING ISSUE BY ID FAIL" ,e);
        }
        if (issues.size() > 0)
            return issues.get(0);
        return null;
    }

    @Override
    public void saveIssues(Issue issue) {
        String values = "'" + issue.getIssueName() + "', '" + issue.getDescription() +
                "', 'Created', '" + issue.getUsername() + "', " + "NOW()";
        String query = "INSERT INTO ISSUE(ISSUE_NAME, DESCRIPTION, STATUS, AUTHOR, DATE) VALUES(" + values + ")";
        try (Connection connection = DataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("SAVING ISSUE FAIL" ,e);
        }
    }
}
