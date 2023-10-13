package com.trainease.service;

import com.trainease.dto.BatchProgress;
import com.trainease.dto.CourseStatus;
import com.trainease.dto.CourseWiseStatusCount;

import java.util.List;

public interface ReportService {

    Integer totalCoursesInBatch(String batchId);

    Integer totalTraineesInBatch(String batchId);

    CourseWiseStatusCount courseIdWiseStatusCountForAdmin(String courseId);

    Integer totalTraineesCountByCourseIdAndStatus(String courseId, CourseStatus courseStatus);

    CourseWiseStatusCount totalCoursesStatusCountForTrainee(String emailId);

    Integer totalStatusCountByEmailIdAndStatus(String emailId, CourseStatus courseStatus);

    List<String> feedbackListByCourseId(String courseId);

    String getTraineeBatchId(String emailId);

    List<BatchProgress> getBatchProgress(String batchId);

}
