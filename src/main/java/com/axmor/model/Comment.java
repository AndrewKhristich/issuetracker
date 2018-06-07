package com.axmor.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Comment {

    private Long id;
    private String userName;
    private Long articleId;
    private String commentDescription;
    private String status;
    private Timestamp date;

    public Comment() {

    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", articleId=" + articleId +
                ", commentDescription='" + commentDescription + '\'' +
                '}';
    }
}
