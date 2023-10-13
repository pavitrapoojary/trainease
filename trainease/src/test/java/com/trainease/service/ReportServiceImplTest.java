package com.trainease.service;

import com.trainease.dto.BatchProgress;
import com.trainease.dto.CourseStatus;
import com.trainease.dto.CourseWiseStatusCount;
import com.trainease.dto.UserRole;
import com.trainease.entity.*;
import com.trainease.repository.CourseProgressRepository;
import com.trainease.repository.CourseRepository;
import com.trainease.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseProgressRepository courseProgressRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void totalCoursesInBatch() {
        Course course1 = Course.builder().courseId("C1").batch(Batch.builder().batchId("B1").build()).build();
        Course course2 = Course.builder().courseId("C2").batch(Batch.builder().batchId("B1").build()).build();
        Course course3 = Course.builder().courseId("C3").batch(Batch.builder().batchId("B2").build()).build();
        List<Course> courseList = Arrays.asList(course1, course2, course3);

        courseRepository.saveAll(courseList);
        when(courseRepository.countByBatch_BatchId("B1")).thenReturn(2L);
        Integer actualResult = reportService.totalCoursesInBatch("B1");
        assertEquals(2, actualResult);
        verify(courseRepository).countByBatch_BatchId("B1");
    }

    @Test
    void totalTraineesInBatch() {
        User user1 = User.builder().emailId("admin@gmail.com").role(UserRole.ADMIN).build();
        User user2 = User.builder().emailId("trainee1@gmail.com").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B1").build()).build();
        User user3 = User.builder().emailId("trainee2@gmail.com").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B1").build()).build();
        User user4 = User.builder().emailId("trainee3@gmail.com").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B2").build()).build();

        List<User> userList = Arrays.asList(user1, user2, user3, user4);
        userRepository.saveAll(userList);
        when(userRepository.countByBatch_BatchId("B1")).thenReturn(2L);

        Integer actualResult = reportService.totalTraineesInBatch("B1");
        assertEquals(2, actualResult);
        verify(userRepository).countByBatch_BatchId("B1");
    }

    @Test
    void totalTraineesCountByCourseIdAndStatus() {
        when(courseProgressRepository.countByCourse_CourseIdAndStatus("C1", CourseStatus.TO_BE_STARTED)).thenReturn(2L);
        when(courseProgressRepository.countByCourse_CourseIdAndStatus("C1", CourseStatus.IN_PROGRESS)).thenReturn(3L);
        when(courseProgressRepository.countByCourse_CourseIdAndStatus("C1", CourseStatus.COMPLETED)).thenReturn(1L);

        CourseWiseStatusCount actualResult = reportService.courseIdWiseStatusCountForAdmin("C1");
        assertEquals(2, actualResult.getToBeStartedCount());
    }

    @Test
    void totalCoursesStatusCountForTrainee() {
        String emailId = "trainee@gmail.com";
        when(courseProgressRepository.countByUser_EmailIdAndStatus(emailId, CourseStatus.TO_BE_STARTED)).thenReturn(2L);
        when(courseProgressRepository.countByUser_EmailIdAndStatus(emailId, CourseStatus.IN_PROGRESS)).thenReturn(3L);
        when(courseProgressRepository.countByUser_EmailIdAndStatus(emailId, CourseStatus.COMPLETED)).thenReturn(1L);

        CourseWiseStatusCount actualResult = reportService.totalCoursesStatusCountForTrainee(emailId);
        assertEquals(2, actualResult.getToBeStartedCount());
        assertEquals(3, actualResult.getInProgressCount());
        assertEquals(1, actualResult.getCompletedCount());
    }

    @Test
    void feedbackListByCourseId() {
        CourseProgress courseProgress1 = CourseProgress.builder().progressId(1).course(Course.builder().courseId("C1").build()).feedback("Feedback 1").build();
        CourseProgress courseProgress2 = CourseProgress.builder().progressId(2).course(Course.builder().courseId("C1").build()).feedback("Feedback 2").build();
        CourseProgress courseProgress3 = CourseProgress.builder().progressId(3).course(Course.builder().courseId("C1").build()).build();
        List<CourseProgress> courseProgressList = Arrays.asList(courseProgress1, courseProgress2, courseProgress3);
        when(courseProgressRepository.findByCourseId("C1")).thenReturn(courseProgressList);
        List<String> actualFeedbackList = reportService.feedbackListByCourseId("C1");
        assertEquals(2, actualFeedbackList.size());

    }

    @Test
    void getTraineeBatchId() {
        String emailId = "trainee@gmail.com";
        User user = User.builder().emailId(emailId).batch(Batch.builder().batchId("B1").build()).build();
        when(userRepository.findById(emailId)).thenReturn(Optional.ofNullable(user));
        String actualResult = reportService.getTraineeBatchId(emailId);
        assertEquals("B1", actualResult);
    }

    @Test
    void getBatchProgress() {
        CourseProgress courseProgress1 = CourseProgress.builder().user(User.builder().emailId("1@email.com").build())
                .batch(Batch.builder().batchId("B1").build())
                .course(Course.builder().courseId("C1").build())
                .status(CourseStatus.TO_BE_STARTED)
                .build();
        CourseProgress courseProgress2 = CourseProgress.builder().user(User.builder().emailId("2@email.com").build())
                .batch(Batch.builder().batchId("B1").build())
                .course(Course.builder().courseId("C1").build())
                .status(CourseStatus.TO_BE_STARTED)
                .build();
        List<CourseProgress> courseProgressBatchB1 = Arrays.asList(courseProgress1, courseProgress2);

        when(courseProgressRepository.findBatchProgressByBatchId("B1")).thenReturn(courseProgressBatchB1);

        List<BatchProgress> actualResult = reportService.getBatchProgress("B1");

        assertNotNull(actualResult);
        assertEquals(2, actualResult.size());
        verify(courseProgressRepository).findBatchProgressByBatchId("B1");
    }

}