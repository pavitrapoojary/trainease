package com.trainease.service;

import com.trainease.dto.BatchProgress;
import com.trainease.dto.CourseProgressStatus;
import com.trainease.dto.CourseStatus;
import com.trainease.dto.CourseWiseStatusCount;
import com.trainease.entity.*;
import com.trainease.repository.CourseProgressRepository;
import com.trainease.repository.CourseRepository;
import com.trainease.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private CourseProgressRepository courseProgressRepository;

    @Override
    public Integer totalCoursesInBatch(String batchId) {
        return Math.toIntExact(courseRepository.countByBatch_BatchId(batchId));
    }

    @Override
    public Integer totalTraineesInBatch(String batchId) {
        return Math.toIntExact(userRepository.countByBatch_BatchId(batchId));
    }

    @Override
    public CourseWiseStatusCount courseIdWiseStatusCountForAdmin(String courseId) {
        return CourseWiseStatusCount.builder()
                .toBeStartedCount(totalTraineesCountByCourseIdAndStatus(courseId, CourseStatus.TO_BE_STARTED))
                .inProgressCount(totalTraineesCountByCourseIdAndStatus(courseId, CourseStatus.IN_PROGRESS))
                .completedCount(totalTraineesCountByCourseIdAndStatus(courseId, CourseStatus.COMPLETED))
                .build();
    }

    @Override
    public Integer totalTraineesCountByCourseIdAndStatus(String courseId, CourseStatus courseStatus) {
        return Math.toIntExact(courseProgressRepository.countByCourse_CourseIdAndStatus(courseId, courseStatus));
    }

    @Override
    public CourseWiseStatusCount totalCoursesStatusCountForTrainee(String emailId) {
        return CourseWiseStatusCount.builder()
                .toBeStartedCount(totalStatusCountByEmailIdAndStatus(emailId, CourseStatus.TO_BE_STARTED))
                .inProgressCount(totalStatusCountByEmailIdAndStatus(emailId, CourseStatus.IN_PROGRESS))
                .completedCount(totalStatusCountByEmailIdAndStatus(emailId, CourseStatus.COMPLETED))
                .build();
    }

    @Override
    public Integer totalStatusCountByEmailIdAndStatus(String emailId, CourseStatus courseStatus) {
        return Math.toIntExact(courseProgressRepository.countByUser_EmailIdAndStatus(emailId, courseStatus));
    }

    @Override
    public List<String> feedbackListByCourseId(String courseId) {
        List<String> feedbackList = new ArrayList<>();
        List<CourseProgress> courseProgressByCourseId = courseProgressRepository.findByCourseId(courseId);
        courseProgressByCourseId.stream()
                .filter(courseProgress -> courseProgress.getFeedback() != null)
                .map(CourseProgress::getFeedback)
                .forEach(feedbackList::add);
        return feedbackList;
    }

    @Override
    public String getTraineeBatchId(String emailId) {
        return userRepository.findById(emailId).get().getBatch().getBatchId();
    }

    @Override
    public List<BatchProgress> getBatchProgress(String batchId) {
        List<CourseProgress> courseProgressList = courseProgressRepository.findBatchProgressByBatchId(batchId);

        return courseProgressList.stream()
                .collect(Collectors.groupingBy(
                        courseProgress -> courseProgress.getUser().getEmailId(),
                        Collectors.mapping(this::mapToCourseProgressStatus, Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> BatchProgress.builder()
                        .emailId(entry.getKey())
                        .courseProgressStatusList(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private CourseProgressStatus mapToCourseProgressStatus(CourseProgress courseProgress) {
        return CourseProgressStatus.builder()
                .courseName(courseProgress.getCourse().getCourseName())
                .courseStatus(courseProgress.getStatus())
                .build();
    }


}
