package com.trainease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "course_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Integer progressId;

    @ManyToOne
    @JoinColumn(name = "email_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "status")
    private CourseStatus status;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "actual_start_date")
    private Date actualStartDate;

    @Column(name = "actual_end_date")
    private Date actualEndDate;
}
