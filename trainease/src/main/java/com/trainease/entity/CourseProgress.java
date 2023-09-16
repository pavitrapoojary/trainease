package com.trainease.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "course_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseProgress {
    private String emailId;
    private String batchId;
    private String courseId;
    private String courseName;
    private String description;
    private String link;
    private double durationInHours;
    private Date estimatedStartDate;
    private Date estimatedEndDate;
    private List<SubjectMatterExpert> subjectMatterExpert;
    private CourseStatus status;
    private String feedback;
    private Date actualStartDate;
    private Date actualEndDate;
}
