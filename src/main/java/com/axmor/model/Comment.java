package com.axmor.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {

    private Long id;
    private String userName;
    private Long issueId;
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
                ", issueId=" + issueId +
                ", commentDescription='" + commentDescription + '\'' +
                '}';
    }
}
