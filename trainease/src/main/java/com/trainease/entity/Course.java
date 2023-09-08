package com.trainease.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
public class Course {

    @Id
    private String courseId;
    private String batchId;
    private String courseName;
    private String description;
    private String link;
    private double durationInHours;
    private Date estimatedStartDate;
    private Date estimatedEndDate;
    private List<String>subjectMatterExpert;

    public Course() {
    }

    public Course(String courseId, String batchId, String courseName, String description, String link, double durationInHours, Date estimatedStartDate, Date estimatedEndDate, List<String> subjectMatterExpert) {
        this.courseId = courseId;
        this.batchId = batchId;
        this.courseName = courseName;
        this.description = description;
        this.link = link;
        this.durationInHours = durationInHours;
        this.estimatedStartDate = estimatedStartDate;
        this.estimatedEndDate = estimatedEndDate;
        this.subjectMatterExpert = subjectMatterExpert;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(double durationInHours) {
        this.durationInHours = durationInHours;
    }

    public Date getEstimatedStartDate() {
        return estimatedStartDate;
    }

    public void setEstimatedStartDate(Date estimatedStartDate) {
        this.estimatedStartDate = estimatedStartDate;
    }

    public Date getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(Date estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    public List<String> getSubjectMatterExpert() {
        return subjectMatterExpert;
    }

    public void setSubjectMatterExpert(List<String> subjectMatterExpert) {
        this.subjectMatterExpert = subjectMatterExpert;
    }
}
