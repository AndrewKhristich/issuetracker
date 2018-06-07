package com.axmor.dao;

import com.axmor.model.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> findAllCommentsByArticleId(Long id);

    void saveComment(Comment comment);
}
