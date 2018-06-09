package com.axmor.dao;

import com.axmor.model.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> findAllCommentsByIssueId(Long id);

    void saveComment(Comment comment);
}
