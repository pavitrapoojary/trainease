package com.trainease.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private List<SubjectMatterExpert> subjectMatterExpert;
}
