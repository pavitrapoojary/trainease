package com.trainease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @Column(name = "course_id")
    private String courseId;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "description")
    private String description;

    @Column(name = "link")
    private String link;

    @Column(name = "duration_in_hours")
    private double durationInHours;

    @Column(name = "estimated_start_date")
    private Date estimatedStartDate;

    @Column(name = "estimated_end_date")
    private Date estimatedEndDate;

    @Column(name = "subject_matter_expert")
    private String subjectMatterExpert;
}
