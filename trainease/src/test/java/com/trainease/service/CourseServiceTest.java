package com.trainease.service;

import com.trainease.entity.BatchWiseCourses;
import com.trainease.entity.Course;
import com.trainease.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository = mock(CourseRepository.class);

    @InjectMocks
    private CourseServiceImpl courseService = new CourseServiceImpl(courseRepository);

    @Test
    void getCoursesByBatchId() {
        Course course = Course.builder().courseId("C1").batchId("B1").courseName("cName").description("cDescription").build();
        Course course1 = Course.builder().courseId("C2").batchId("B1").courseName("cName").description("cDescription").build();
        Course course2 = Course.builder().courseId("C3").batchId("B2").courseName("cName").description("cDescription").build();
        List<Course> allCoursesList = Arrays.asList(course,course1,course2);
        when(courseRepository.findAll()).thenReturn(allCoursesList);
        assertEquals(2,courseService.getCoursesByBatchId("B1").size());
        verify(courseRepository).findAll();
    }

    @Test
    void getAllBatchDetails(){
        Course course = Course.builder().courseId("C1").batchId("B1").courseName("java").build();
        Course course1 = Course.builder().courseId("C2").batchId("B1").courseName("spring").build();
        Course course2 = Course.builder().courseId("C4").batchId("B2").courseName("javascript").build();
        List<Course>courseList = Arrays.asList(course,course1,course2);
        when(courseRepository.findAll()).thenReturn(courseList);
        List<BatchWiseCourses> actualBatchCoursWises = courseService.getAllBatchWiseCourses();
        assertEquals(2, actualBatchCoursWises.size());
        verify(courseRepository).findAll();
    }

    @Test
    void createCourse() {
        Course newCourse = Course.builder().courseId("C1").batchId("B1").courseName("cName").build();
        when(courseRepository.save(newCourse)).thenReturn(newCourse);
        Course createdCourse = courseService.createCourse(newCourse);
        assertNotNull(createdCourse);
        assertEquals(newCourse.getCourseId(), createdCourse.getCourseId());
        assertEquals(newCourse.getCourseName(), createdCourse.getCourseName());
        assertEquals(newCourse.getBatchId(), createdCourse.getBatchId());
    }

    @Test
    void deleteCourseByCourseIdWhenCourseExists(){
        String courseId = "C1";
        Course course = Course.builder().courseId(courseId).batchId("B1").courseName("cName").build();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        String actualResult = courseService.deleteCourseByCourseId(courseId);
        assertEquals("Course ID : "+courseId+" has been deleted successfully!", actualResult);
        verify(courseRepository).deleteById(courseId);
    }

    @Test
    void deleteCourseByCourseIdWhenCourseDoesNotExist(){
        String courseId = "C1";
        Course course = Course.builder().courseId(courseId).batchId("B1").courseName("cName").build();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        String actualResult = courseService.deleteCourseByCourseId(courseId);
        assertEquals("Course ID : "+courseId+" does not exist.", actualResult);
        verify(courseRepository,never()).deleteById(courseId);
    }
}
