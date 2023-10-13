package com.trainease.controller;

import com.trainease.dto.BatchProgress;
import com.trainease.dto.CourseWiseStatusCount;
import com.trainease.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/report/totalCourses/{batchId}")
    public Integer totalCoursesInBatch(@PathVariable String batchId) {
        return this.reportService.totalCoursesInBatch(batchId);
    }

    @GetMapping("/report/totalTrainees/{batchId}")
    public Integer totalTraineesInBatch(@PathVariable String batchId) {
        return this.reportService.totalTraineesInBatch(batchId);
    }

    @GetMapping("/report/status/admin/{courseId}")
    public CourseWiseStatusCount courseIdWiseStatusCountForAdmin(@PathVariable String courseId) {
        return this.reportService.courseIdWiseStatusCountForAdmin(courseId);
    }

    @GetMapping("/report/feedback/{courseId}")
    public List<String> feedbackListByCourseId(@PathVariable String courseId) {
        return this.reportService.feedbackListByCourseId(courseId);
    }

    @GetMapping("/report/status/{emailId}")
    public CourseWiseStatusCount totalCoursesStatusCountForTrainee(@PathVariable String emailId) {
        return this.reportService.totalCoursesStatusCountForTrainee(emailId);
    }

    @GetMapping("/report/batchId/{emailId}")
    public String getTraineeBatchId(@PathVariable String emailId) {
        return this.reportService.getTraineeBatchId(emailId);
    }

    @GetMapping("/report/batch/{batchId}/progress")
    public List<BatchProgress> getBatchProgressByBatchId(@PathVariable String batchId) {
        return this.reportService.getBatchProgress(batchId);
    }

}
