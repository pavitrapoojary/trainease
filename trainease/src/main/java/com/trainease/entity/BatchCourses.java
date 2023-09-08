package com.trainease.entity;

import jakarta.persistence.Id;

import java.util.List;

public class BatchCourses {

    private String batchId;

    private List<Course> courses;

    public BatchCourses() {
    }

    public BatchCourses(String batchId, List<Course> courses) {
        this.batchId = batchId;
        this.courses = courses;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
