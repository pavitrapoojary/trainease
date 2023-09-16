package com.trainease.service;

import com.trainease.entity.CourseProgress;
import com.trainease.entity.CourseStatus;
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
                .emailId(traineeEmailId).batchId("B1").courseId("C1").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress courseProgress1 = CourseProgress.builder()
                .emailId(traineeEmailId).batchId("B1").courseId("C2").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress courseProgress2 = CourseProgress.builder()
                .emailId("trainee3@gmail.com").batchId("B2").courseId("C3").courseName("cName")
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
                .emailId(traineeEmailId).batchId("B1").courseId("C1").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress courseProgress1 = CourseProgress.builder()
                .emailId(traineeEmailId).batchId("B1").courseId("C2").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress courseProgress2 = CourseProgress.builder()
                .emailId("trainee3@gmail.com").batchId("B2").courseId("C3").courseName("cName")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        List<CourseProgress> allCourseProgressList = Arrays.asList(courseProgress, courseProgress1, courseProgress2);
        when(courseProgressRepository.findAll()).thenReturn(allCourseProgressList);
        List<CourseProgress> actualCourseProgressList = courseProgressService.getCoursesProgressByTraineeEmailId("doesnotexist@gmail.com");
        assertEquals(0, actualCourseProgressList.size());
    }

    @Test
    void updateCourseProgressWhenProgressExists() {
        String emailId = "trainee1@gmail.com";
        CourseProgress existingCourseProgress = CourseProgress.builder()
                .emailId(emailId).batchId("B1").courseId("C1")
                .status(CourseStatus.TO_BE_STARTED).feedback("NA").build();
        CourseProgress updatedCourseProgress = CourseProgress.builder()
                .emailId(emailId).batchId("B1").courseId("C1")
                .status(CourseStatus.IN_PROGRESS).feedback("NA").build();
        when(courseProgressRepository.findByEmailIdAndCourseId(emailId, existingCourseProgress.getCourseId())).thenReturn(Optional.of(existingCourseProgress));
        when(courseProgressRepository.save(any(CourseProgress.class))).thenReturn(updatedCourseProgress);
        String actualResult = courseProgressService.updateCourseProgress(updatedCourseProgress);
        assertEquals("Progress for Course ID : "+existingCourseProgress.getCourseId()+" has been updated!", actualResult);
        verify(courseProgressRepository).findByEmailIdAndCourseId(emailId, existingCourseProgress.getCourseId());
        verify(courseProgressRepository).save(any(CourseProgress.class));
    }

    @Test
    void updateCourseProgressWhenProgressDoesNotExist() {
        String emailId = "trainee1@gmail.com";
        CourseProgress updatedCourseProgress = CourseProgress.builder()
                .emailId(emailId).batchId("B1").courseId("C1")
                .status(CourseStatus.IN_PROGRESS).feedback("NA").build();
        when(courseProgressRepository.findByEmailIdAndCourseId(emailId, updatedCourseProgress.getCourseId())).thenReturn(Optional.empty());
        String actualResult = courseProgressService.updateCourseProgress(updatedCourseProgress);
        assertEquals("Course ID : "+updatedCourseProgress.getCourseId()+" does not exist for Trainee Email ID : "+updatedCourseProgress.getEmailId(), actualResult);
        verify(courseProgressRepository, never()).save(any(CourseProgress.class));
    }
}

















