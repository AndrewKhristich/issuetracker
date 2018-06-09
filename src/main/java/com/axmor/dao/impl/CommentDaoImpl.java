package com.axmor.dao.impl;

import com.axmor.dao.CommentDao;
import com.axmor.model.Comment;
import com.axmor.utils.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static com.axmor.utils.CustomMapper.mapCommentFromResultSet;

public class CommentDaoImpl implements CommentDao {

    private static final Logger LOG = LoggerFactory.getLogger(CommentDaoImpl.class);


    @Override
    public List<Comment> findAllCommentsByIssueId(Long id) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM COMMENT WHERE ISSUE_ID = " + id + " ORDER BY PUBLISHED_DATE";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                comments.add(mapCommentFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("FINDING ALL COMMENTS FAIL" ,e);
        }
        return comments;
    }

    @Override
    public void saveComment(Comment comment) {
        String status = comment.getStatus();

        boolean resolved = status.equals("Resolved");
        if (!resolved) status = null;
        else updateIssue(comment.getIssueId());

        String commentValue = ("'" + comment.getUserName() + "', '"
                + comment.getIssueId() + "', '"
                + comment.getCommentDescription() + "', " +
                "NOW(), '" + status + "'");
        String commentQuery = "INSERT INTO COMMENT(USERNAME, ISSUE_ID, COMMENT_DESCRIPTION, PUBLISHED_DATE, STATUS) VALUES(" + commentValue + ")";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statementForComment = connection.prepareStatement(commentQuery)) {
            statementForComment.executeUpdate();
        } catch (SQLException e) {
            LOG.error("SAVING COMMENT FAIL" ,e);
        }
    }

    private void updateIssue(Long id) {
        String query = "UPDATE ISSUE SET STATUS='Resolved' WHERE ID=" + id;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("FINDING COMMENT FAIL" ,e);
        }
    }
}
