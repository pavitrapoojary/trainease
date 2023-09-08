package com.trainease.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class User {
    @Id
    private String emailId;
    private String name;
    private String role;
    private String batchId;

    public User() {
    }

    public User(String emailId, String name, String role, String batchId) {
        this.emailId = emailId;
        this.name = name;
        this.role = role;
        this.batchId = batchId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
