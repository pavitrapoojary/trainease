package com.trainease.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainease.dto.CourseStatus;
import com.trainease.entity.Course;
import com.trainease.entity.CourseProgress;
import com.trainease.entity.User;
import com.trainease.service.CourseProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
class CourseProgressControllerTest {
    @Mock
    CourseProgressService courseProgressService;

    @InjectMocks
    CourseProgressController courseProgressController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseProgressController).build();
    }

    @Test
    void testGetCoursesProgressByTraineeEmailId() throws Exception {
        String emailId = "test@example.com";
        CourseProgress courseProgress1 = CourseProgress.builder()
                .user(User.builder().emailId(emailId).build())
                .course(Course.builder().courseId("C1").build())
                .status(CourseStatus.TO_BE_STARTED)
                .build();

        CourseProgress courseProgress2 = CourseProgress.builder()
                .user(User.builder().emailId(emailId).build())
                .course(Course.builder().courseId("C2").build())
                .status(CourseStatus.IN_PROGRESS)
                .build();

        List<CourseProgress> progressList = Arrays.asList(courseProgress1, courseProgress2);

        when(courseProgressService.getCoursesProgressByTraineeEmailId(emailId)).thenReturn(progressList);

        mockMvc.perform(get("/{emailId}/progress", emailId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(courseProgressService, times(1)).getCoursesProgressByTraineeEmailId(emailId);
    }

    @Test
    void testGetCourseProgressByTraineeEmailIdAndCourseId() throws Exception {
        String emailId = "test@example.com";
        String courseId = "course1";
        CourseProgress courseProgress = CourseProgress.builder()
                .user(User.builder().emailId(emailId).build())
                .course(Course.builder().courseId(courseId).build())
                .build();

        when(courseProgressService.getCourseProgressByTraineeEmailIdAndCourseId(emailId, courseId)).thenReturn(courseProgress);

        mockMvc.perform(get("/progress/{emailId}/{courseId}", emailId, courseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());

        verify(courseProgressService, times(1)).getCourseProgressByTraineeEmailIdAndCourseId(emailId, courseId);
    }

    @Test
    void testUpdateCourseProgress() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CourseProgress updatedProgress = new CourseProgress();

        when(courseProgressService.updateCourseProgress(any(CourseProgress.class))).thenReturn(updatedProgress);

        mockMvc.perform(put("/progress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProgress)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());

        verify(courseProgressService, times(1)).updateCourseProgress(any(CourseProgress.class));
    }
}