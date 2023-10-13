package com.trainease.service;

import com.trainease.dto.CourseStatus;
import com.trainease.entity.*;
import com.trainease.repository.CourseProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseProgressServiceImplTest {
    @Mock
    private CourseProgressRepository courseProgressRepository;

    @InjectMocks
    private CourseProgressServiceImpl courseProgressService;

    @Test
    void getCoursesProgressByEmailIdWhenEmailIdExists() {
        String traineeEmailId = "trainee1@gmail.com";
        CourseProgress courseProgress = CourseProgress.builder()
                .user(User.builder().emailId(traineeEmailId).build())
                .batch(Batch.builder().batchId("B1").build())
                .course(Course.builder().courseId("C1").courseName("cName").build())
                .status(CourseStatus.TO_BE_STARTED)
                .feedback("NA")
                .build();

        CourseProgress courseProgress1 = CourseProgress.builder()
                .user(User.builder().emailId(traineeEmailId).build())
                .batch(Batch.builder().batchId("B1").build())
                .course(Course.builder().courseId("C2").courseName("cName").build())
                .status(CourseStatus.TO_BE_STARTED)
                .feedback("NA")
                .build();

        CourseProgress courseProgress2 = CourseProgress.builder()
                .user(User.builder().emailId("trainee3@gmail.com").build())
                .batch(Batch.builder().batchId("B2").build())
                .course(Course.builder().courseId("C3").courseName("cName").build())
                .status(CourseStatus.TO_BE_STARTED)
                .feedback("NA")
                .build();

        List<CourseProgress> allCourseProgressList = Arrays.asList(courseProgress, courseProgress1, courseProgress2);
        List<CourseProgress> expectedCourseProgressList = Arrays.asList(courseProgress, courseProgress1);
        when(courseProgressRepository.findAll()).thenReturn(allCourseProgressList);
        List<CourseProgress> actualCourseProgressList = courseProgressService.getCoursesProgressByTraineeEmailId(traineeEmailId);
        assertArrayEquals(expectedCourseProgressList.toArray(), actualCourseProgressList.toArray());
        verify(courseProgressRepository).findAll();
    }

    @Test
    void getCoursesProgressByEmailIdWhenEmailIdDoesNotExist() {
        String traineeEmailId = "trainee1@gmail.com";
        CourseProgress courseProgress = CourseProgress.builder()
                .user(User.builder().emailId(traineeEmailId).build())
                .batch(Batch.builder().batchId("B1").build())
                .course(Course.builder().courseId("C1").courseName("cName").build())
                .status(CourseStatus.TO_BE_STARTED)
                .feedback("NA")
                .build();

        CourseProgress courseProgress1 = CourseProgress.builder()
                .user(User.builder().emailId(traineeEmailId).build())
                .batch(Batch.builder().batchId("B1").build())
                .course(Course.builder().courseId("C2").courseName("cName").build())
                .status(CourseStatus.TO_BE_STARTED)
                .feedback("NA")
                .build();

        CourseProgress courseProgress2 = CourseProgress.builder()
                .user(User.builder().emailId("trainee3@gmail.com").build())
                .batch(Batch.builder().batchId("B2").build())
                .course(Course.builder().courseId("C3").courseName("cName").build())
                .status(CourseStatus.TO_BE_STARTED)
                .feedback("NA")
                .build();

        List<CourseProgress> allCourseProgressList = Arrays.asList(courseProgress, courseProgress1, courseProgress2);
        when(courseProgressRepository.findAll()).thenReturn(allCourseProgressList);
        List<CourseProgress> actualCourseProgressList = courseProgressService.getCoursesProgressByTraineeEmailId("doesnotexist@gmail.com");
        assertEquals(0, actualCourseProgressList.size());
    }

    @Test
    void getCourseProgressByTraineeEmailIdAndCourseId() {
        String traineeEmailId = "trainee1@gmail.com";
        CourseProgress courseProgress = CourseProgress.builder()
                .user(User.builder().emailId(traineeEmailId).build())
                .batch(Batch.builder().batchId("B1").build())
                .course(Course.builder().courseId("C1").courseName("cName").build())
                .status(CourseStatus.TO_BE_STARTED)
                .feedback("NA")
                .build();

        when(courseProgressRepository.findByEmailIdAndCourseId(traineeEmailId, "C1")).thenReturn(Optional.ofNullable(courseProgress));
        CourseProgress actualProgress = courseProgressService.getCourseProgressByTraineeEmailIdAndCourseId(traineeEmailId, "C1");
        assertNotNull(actualProgress);
        assertEquals(courseProgress.getCourse().getCourseName(), actualProgress.getCourse().getCourseName());
        verify(courseProgressRepository).findByEmailIdAndCourseId(traineeEmailId, "C1");
    }

    @Test
    void updateCourseProgressWhenProgressExists() {
        String emailId = "trainee1@gmail.com";
        CourseProgress existingCourseProgress = CourseProgress.builder()
                .user(User.builder().emailId(emailId).build())
                .batch(Batch.builder().batchId("B1").build())
                .course(Course.builder().courseId("C1").build())
                .status(CourseStatus.TO_BE_STARTED)
                .feedback("NA")
                .build();

        CourseProgress updatedCourseProgress = CourseProgress.builder()
                .user(User.builder().emailId(emailId).build())
                .batch(Batch.builder().batchId("B1").build())
                .course(Course.builder().courseId("C1").build())
                .status(CourseStatus.IN_PROGRESS)
                .feedback("NA")
                .build();

        when(courseProgressRepository.findByEmailIdAndCourseId(emailId, existingCourseProgress.getCourse().getCourseId())).thenReturn(Optional.of(existingCourseProgress));
        when(courseProgressRepository.save(any(CourseProgress.class))).thenReturn(updatedCourseProgress);
        CourseProgress actualResult = courseProgressService.updateCourseProgress(updatedCourseProgress);
        assertEquals(updatedCourseProgress.getStatus(), actualResult.getStatus());
        verify(courseProgressRepository).findByEmailIdAndCourseId(emailId, existingCourseProgress.getCourse().getCourseId());
        verify(courseProgressRepository).save(any(CourseProgress.class));
    }

}

















