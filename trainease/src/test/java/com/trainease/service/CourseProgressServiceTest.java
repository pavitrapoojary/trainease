package com.trainease.service;

import com.trainease.entity.CourseProgress;
import com.trainease.entity.CourseStatus;
import com.trainease.repository.CourseProgressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseProgressServiceTest {
    @Mock
    private CourseProgressRepository courseProgressRepository = mock(CourseProgressRepository.class);

    @InjectMocks
    private CourseProgressServiceImpl courseProgressService = new CourseProgressServiceImpl(courseProgressRepository);

    @Test
    void getCoursesProgressByEmailIdWhenEmailIdExists() {
        String traineeEmailId = "trainee1@gmail.com";
        CourseProgress courseProgress = CourseProgress.builder()
                .progressId(1).emailId(traineeEmailId).batchId("B1").courseId("C1").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress courseProgress1 = CourseProgress.builder()
                .progressId(2).emailId(traineeEmailId).batchId("B1").courseId("C2").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress courseProgress2 = CourseProgress.builder()
                .progressId(3).emailId("trainee3@gmail.com").batchId("B2").courseId("C3").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
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
                .progressId(1).emailId(traineeEmailId).batchId("B1").courseId("C1").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress courseProgress1 = CourseProgress.builder()
                .progressId(2).emailId(traineeEmailId).batchId("B1").courseId("C2").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress courseProgress2 = CourseProgress.builder()
                .progressId(3).emailId("trainee3@gmail.com").batchId("B2").courseId("C3").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        List<CourseProgress> allCourseProgressList = Arrays.asList(courseProgress, courseProgress1, courseProgress2);
        when(courseProgressRepository.findAll()).thenReturn(allCourseProgressList);
        List<CourseProgress> actualCourseProgressList = courseProgressService.getCoursesProgressByTraineeEmailId("doesnotexist@gmail.com");
        assertEquals(0, actualCourseProgressList.size());
    }

    @Test
    void updateCourseProgressWhenProgressIdExists() {
        int progressId = 1;
        String emailId = "trainee1@gmail.com";
        CourseProgress existingCourseProgress = CourseProgress.builder()
                .progressId(progressId).emailId(emailId).batchId("B1").courseId("C1")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress updatedCourseProgress = CourseProgress.builder()
                .progressId(progressId).emailId(emailId).batchId("B1").courseId("C1")
                .status(CourseStatus.IN_PROGRESS).feedback("NA").build();
        when(courseProgressRepository.findById(progressId)).thenReturn(Optional.ofNullable(existingCourseProgress));
        when(courseProgressRepository.save(any(CourseProgress.class))).thenReturn(updatedCourseProgress);
        String actualResult = courseProgressService.updateCourseProgress(emailId, progressId, updatedCourseProgress);
        assertEquals("Course progress for batchId:B1 & courseId:C1 has been updated successfully!", actualResult);
        verify(courseProgressRepository).findById(progressId);
        verify(courseProgressRepository).save(any(CourseProgress.class));
    }

    @Test
    void updateCourseProgressWhenProgressIdDoesNotExist() {
        int progressId = 1;
        String emailId = "trainee1@gmail.com";
        CourseProgress updatedCourseProgress = CourseProgress.builder()
                .progressId(progressId).emailId(emailId).batchId("B1").courseId("C1")
                .status(CourseStatus.IN_PROGRESS).feedback("NA").build();
        when(courseProgressRepository.findById(progressId)).thenReturn(Optional.empty());
        String actualResult = courseProgressService.updateCourseProgress(emailId, progressId, updatedCourseProgress);
        assertEquals("Given progressId:1 does not exist.", actualResult);
        verify(courseProgressRepository,never()).save(any(CourseProgress.class));
    }
}

















