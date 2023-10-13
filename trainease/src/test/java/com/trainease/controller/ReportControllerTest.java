package com.trainease.controller;

import com.trainease.dto.BatchProgress;
import com.trainease.dto.CourseWiseStatusCount;
import com.trainease.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class ReportControllerTest {

    @Mock
    ReportService reportService;

    @InjectMocks
    ReportController reportController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    void testTotalCoursesInBatch() throws Exception {
        String batchId = "batch1";
        when(reportService.totalCoursesInBatch(batchId)).thenReturn(5);

        mockMvc.perform(get("/report/totalCourses/{batchId}", batchId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(5));

        verify(reportService, times(1)).totalCoursesInBatch(batchId);
    }

    @Test
    void testTotalTraineesInBatch() throws Exception {
        String batchId = "batch1";
        when(reportService.totalTraineesInBatch(batchId)).thenReturn(10);

        mockMvc.perform(get("/report/totalTrainees/{batchId}", batchId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(10));

        verify(reportService, times(1)).totalTraineesInBatch(batchId);
    }

    @Test
    void testCourseIdWiseStatusCountForAdmin() throws Exception {
        String courseId = "course1";
        CourseWiseStatusCount statusCount = CourseWiseStatusCount.builder()
                .toBeStartedCount(5)
                .inProgressCount(3)
                .completedCount(2)
                .build();

        when(reportService.courseIdWiseStatusCountForAdmin(courseId)).thenReturn(statusCount);

        mockMvc.perform(get("/report/status/admin/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.toBeStartedCount").value(5))
                .andExpect(jsonPath("$.inProgressCount").value(3))
                .andExpect(jsonPath("$.completedCount").value(2));

        verify(reportService, times(1)).courseIdWiseStatusCountForAdmin(courseId);
    }

    @Test
    void testFeedbackListByCourseId() throws Exception {
        String courseId = "course1";
        List<String> feedbackList = Arrays.asList("Feedback 1", "Feedback 2");

        when(reportService.feedbackListByCourseId(courseId)).thenReturn(feedbackList);

        mockMvc.perform(get("/report/feedback/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("Feedback 1"))
                .andExpect(jsonPath("$[1]").value("Feedback 2"));

        verify(reportService, times(1)).feedbackListByCourseId(courseId);
    }

    @Test
    void testTotalCoursesStatusCountForTrainee() throws Exception {
        String emailId = "test@example.com";
        CourseWiseStatusCount statusCount = CourseWiseStatusCount.builder()
                .toBeStartedCount(5)
                .inProgressCount(3)
                .completedCount(2)
                .build();

        when(reportService.totalCoursesStatusCountForTrainee(emailId)).thenReturn(statusCount);

        mockMvc.perform(get("/report/status/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.toBeStartedCount").value(5))
                .andExpect(jsonPath("$.inProgressCount").value(3))
                .andExpect(jsonPath("$.completedCount").value(2));

        verify(reportService, times(1)).totalCoursesStatusCountForTrainee(emailId);
    }

    @Test
    void testGetTraineeBatchId() throws Exception {
        String emailId = "test@example.com";
        when(reportService.getTraineeBatchId(emailId)).thenReturn("batch1");

        mockMvc.perform(get("/report/batchId/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(jsonPath("$").value("batch1"));

        verify(reportService, times(1)).getTraineeBatchId(emailId);
    }

    @Test
    void testGetBatchProgressByBatchId() throws Exception {
        String batchId = "batch1";
        BatchProgress progress1 = new BatchProgress();
        BatchProgress progress2 = new BatchProgress();
        List<BatchProgress> progressList = Arrays.asList(progress1, progress2);

        when(reportService.getBatchProgress(batchId)).thenReturn(progressList);

        mockMvc.perform(get("/report/batch/{batchId}/progress", batchId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(reportService, times(1)).getBatchProgress(batchId);
    }

}