package com.trainease.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer progressId;
    private String emailId;
    private String batchId;
    private String courseId;
    private String courseName;
    private String description;
    private String link;
    private double durationInHours;
    private Date estimatedStartDate;
    private Date estimatedEndDate;
    private List<String> subjectMatterExpert;
    private String status;
    private String feedback;
    private Date actualStartDate;
    private Date actualEndDate;
}
