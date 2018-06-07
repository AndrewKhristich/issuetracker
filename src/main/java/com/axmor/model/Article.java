package com.axmor.model;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class Article {

    private Long id;
    private String status;
    private String articleName;
    private String description;
    private Date date;
    private String username;


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", status=" + status +
                ", articleName='" + articleName + '\'' +
                ", description='" + description + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
