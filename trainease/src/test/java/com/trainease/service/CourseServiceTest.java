package com.trainease.service;

import com.trainease.entity.BatchCourses;
import com.trainease.entity.Course;
import com.trainease.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
        List<String> smes = Arrays.asList("sme1","sme2");
        Course course = new Course("C1","B1",  "cName", "cDescription", "cLink", 12.3, new Date(2023, 9, 6),
                new Date(2023, 10, 6), smes);
        Course course1 = new Course( "C2", "B1","cName", "cDescription", "cLink", 13.3, new Date(2023, 9, 7),
                new Date(2023, 10, 8), smes);
        Course course2 = new Course( "C3","B2", "cName", "cDescription", "cLink", 3.9, new Date(2023, 9, 20),
                new Date(2023, 10, 24), smes);
        List<Course> allCoursesList = Arrays.asList(course,course1,course2);
        when(courseRepository.findAll()).thenReturn(allCoursesList);
        assertEquals(2,courseService.getCoursesByBatchId("B1").size());
        verify(courseRepository).findAll();
    }

    @Test
    void getAllBatchDetails(){
        Course course = new Course();
        course.setCourseId("C1");
        course.setBatchId("B1");
        course.setCourseName("java");

        Course course1 = new Course();
        course1.setCourseId("C2");
        course1.setBatchId("B1");
        course1.setCourseName("spring");

        Course course2 = new Course();
        course2.setCourseId("C1");
        course2.setBatchId("B2");
        course2.setCourseName("javascript");

        List<Course>courseList = Arrays.asList(course,course1,course2);
        when(courseRepository.findAll()).thenReturn(courseList);
        List<BatchCourses> actualBatchCourses = courseService.getAllBatchDetails();
        assertEquals(2,actualBatchCourses.size());
        verify(courseRepository).findAll();
    }

    @Test
    void createCourse() {
        List<String> smes = Arrays.asList("sme1","sme2");
        Course newCourse = new Course( "C1","B1", "cName", "cDescription", "cLink", 12.3, new Date(2023, 9, 6),
                new Date(2023, 10, 6), smes);
        when(courseRepository.save(newCourse)).thenReturn(newCourse);
        Course createdCourse = courseService.createCourse(newCourse);
        assertNotNull(createdCourse);
        assertEquals(newCourse.getCourseId(), createdCourse.getCourseId());
        assertEquals(newCourse.getCourseName(), createdCourse.getCourseName());
        assertEquals(newCourse.getBatchId(), createdCourse.getBatchId());
    }
}
