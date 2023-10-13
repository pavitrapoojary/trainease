package com.trainease.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainease.entity.Batch;
import com.trainease.entity.Course;
import com.trainease.service.CourseService;
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

import org.springframework.mock.web.MockMultipartFile;


@RunWith(MockitoJUnitRunner.class)
class CourseControllerTest {
    @Mock
    CourseService courseService;

    @InjectMocks
    CourseController courseController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void testGetAllCourses() throws Exception {
        Course course1 = Course.builder()
                .courseId("C1").courseName("C1_name").build();
        Course course2 = Course.builder()
                .courseId("C2").courseName("C2_name").build();

        List<Course> courseList = Arrays.asList(course1, course2);

        when(courseService.getAllCourses()).thenReturn(courseList);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].courseName").value(course1.getCourseName()))
                .andExpect(jsonPath("$[1].courseName").value(course2.getCourseName()));

        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetCoursesByBatchId() throws Exception {
        String batchId = "B1";
        Course course1 = Course.builder()
                .courseId("C1").courseName("C1_name")
                .batch(Batch.builder().batchId(batchId).build()).build();
        Course course2 = Course.builder()
                .courseId("C2").courseName("C2_name")
                .batch(Batch.builder().batchId(batchId).build()).build();

        List<Course> courseList = Arrays.asList(course1, course2);

        when(courseService.getCoursesByBatchId(batchId)).thenReturn(courseList);

        mockMvc.perform(get("/{batchId}/courses", batchId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].courseName").value(course1.getCourseName()))
                .andExpect(jsonPath("$[1].courseName").value(course2.getCourseName()));

        verify(courseService, times(1)).getCoursesByBatchId(batchId);
    }

    @Test
    void testCreateCourse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Course newCourse = Course.builder()
                .courseId("C1").courseName("C1_name")
                .batch(Batch.builder().batchId("B1").build()).build();

        when(courseService.createCourse(any(Course.class))).thenReturn(newCourse);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCourse)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.courseName").value(newCourse.getCourseName()));

        verify(courseService, times(1)).createCourse(any(Course.class));
    }

    @Test
    void testCreateCourses() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Course course1 = Course.builder()
                .courseId("C1").courseName("C1_name")
                .batch(Batch.builder().batchId("B1").build()).build();
        Course course2 = Course.builder()
                .courseId("C2").courseName("C2_name")
                .batch(Batch.builder().batchId("B1").build()).build();

        List<Course> newCourses = Arrays.asList(course1, course2);

        when(courseService.createCourses(anyList())).thenReturn(newCourses);

        mockMvc.perform(post("/courses/saveAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCourses)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].courseName").value(course1.getCourseName()))
                .andExpect(jsonPath("$[1].courseName").value(course2.getCourseName()));

        verify(courseService, times(1)).createCourses(anyList());
    }

    @Test
    void testUpdateCourse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Course updatedCourse = Course.builder()
                .courseId("C1").courseName("Updated Course")
                .batch(Batch.builder().batchId("B1").build()).build();

        when(courseService.updateCourse(any(Course.class))).thenReturn(updatedCourse);

        mockMvc.perform(put("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCourse)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.courseName").value(updatedCourse.getCourseName()));

        verify(courseService, times(1)).updateCourse(any(Course.class));
    }

    @Test
    void testDeleteCourseByCourseId() throws Exception {
        String courseId = "course1";
        when(courseService.deleteCourseByCourseId(courseId)).thenReturn("Course ID : " + courseId + " has been deleted successfully!");

        mockMvc.perform(delete("/courses/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(content().string("Course ID : " + courseId + " has been deleted successfully!"));

        verify(courseService, times(1)).deleteCourseByCourseId(courseId);
    }

    @Test
    void testSaveCoursesFromExcel() throws Exception {
        MockMultipartFile excelFile = new MockMultipartFile("file",
                "test.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE,
                "excel content".getBytes());

        Course course1 = Course.builder()
                .courseId("C1").courseName("C1_name")
                .batch(Batch.builder().batchId("B1").build()).build();
        Course course2 = Course.builder()
                .courseId("C2").courseName("C2_name")
                .batch(Batch.builder().batchId("B1").build()).build();

        List<Course> courseList = Arrays.asList(course1, course2);

        when(courseService.saveCoursesFromExcel(any(MockMultipartFile.class))).thenReturn(courseList);

        mockMvc.perform(multipart("/courses/saveExcel").file(excelFile))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].courseName").value(course1.getCourseName()))
                .andExpect(jsonPath("$[1].courseName").value(course2.getCourseName()));

        verify(courseService, times(1)).saveCoursesFromExcel(any(MockMultipartFile.class));
    }

}