package com.axmor.utils;

import com.axmor.model.Issue;
import com.axmor.model.Comment;
import com.axmor.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Custom mapper from ResultSet to model class
 */
public class CustomMapper {

    /**
     * @param set ResultSet
     * @return Issue object
     */
    public static Issue mapIssueFromResultSet(ResultSet set) throws SQLException {
        Issue issue = new Issue();
        issue.setId(set.getLong("id"));
        issue.setStatus(set.getString("status"));
        issue.setIssueName(set.getString("issue_name"));
        issue.setDescription(set.getString("description"));
        issue.setUsername(set.getString("author"));
        issue.setDate(set.getDate("date"));
        return issue;
    }

    /**
     * @param set ResultSet
     * @return User object
     */
    public static User mapUserFromResultSet(ResultSet set) throws SQLException {
        User user = new User();
        user.setName(set.getString("name"));
        user.setPassword(set.getString("password"));
        return user;
    }

    /**
     * @param set ResultSet
     *  @return Comment object
     */
    public static Comment mapCommentFromResultSet(ResultSet set) throws SQLException {
        Comment comment = new Comment();
        comment.setId(set.getLong("id"));
        comment.setIssueId(set.getLong("issue_id"));
        comment.setUserName(set.getString("username"));
        comment.setCommentDescription(set.getString("comment_description"));
        comment.setDate(set.getTimestamp("PUBLISHED_DATE"));
        return comment;
    }

}
