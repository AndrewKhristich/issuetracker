package com.axmor.model;

import lombok.Data;

import java.util.Date;

@Data
public class Issue {

    private Long id;
    private String status;
    private String issueName;
    private String description;
    private Date date;
    private String username;


    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", status=" + status +
                ", issueName='" + issueName + '\'' +
                ", description='" + description + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
