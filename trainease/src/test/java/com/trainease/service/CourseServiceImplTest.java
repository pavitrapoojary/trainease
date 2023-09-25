package com.trainease.service;

import com.trainease.ExcelParser;
import com.trainease.entity.Batch;
import com.trainease.entity.Course;
import com.trainease.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ExcelParser excelParser;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void getAllCourses() {
        Course course = Course.builder()
                .courseId("C1")
                .batch(Batch.builder().batchId("B1").build())
                .courseName("cName")
                .description("cDescription")
                .build();

        Course course1 = Course.builder()
                .courseId("C2")
                .batch(Batch.builder().batchId("B1").build())
                .courseName("cName")
                .description("cDescription")
                .build();

        Course course2 = Course.builder()
                .courseId("C3")
                .batch(Batch.builder().batchId("B2").build())
                .courseName("cName")
                .description("cDescription")
                .build();

        List<Course> expectedCoursesList = Arrays.asList(course, course1, course2);
        when(courseRepository.findAll()).thenReturn(expectedCoursesList);
        List<Course> actualCoursesList = courseService.getAllCourses();
        assertArrayEquals(expectedCoursesList.toArray(), actualCoursesList.toArray());
    }

    @Test
    void getCoursesByBatchId() {
        Course course = Course.builder()
                .courseId("C1")
                .batch(Batch.builder().batchId("B1").build())
                .courseName("cName")
                .description("cDescription")
                .build();

        Course course1 = Course.builder()
                .courseId("C2")
                .batch(Batch.builder().batchId("B1").build())
                .courseName("cName")
                .description("cDescription")
                .build();

        Course course2 = Course.builder()
                .courseId("C3")
                .batch(Batch.builder().batchId("B2").build())
                .courseName("cName")
                .description("cDescription")
                .build();

        List<Course> allCoursesList = Arrays.asList(course, course1, course2);
        when(courseRepository.findAll()).thenReturn(allCoursesList);
        assertEquals(2, courseService.getCoursesByBatchId("B1").size());
        verify(courseRepository).findAll();
    }

    @Test
    void createCourse() {
        Course newCourse = Course.builder()
                .courseId("C1")
                .batch(Batch.builder().batchId("B1").build())
                .courseName("cName")
                .description("cDescription")
                .build();
        when(courseRepository.save(newCourse)).thenReturn(newCourse);
        Course createdCourse = courseService.createCourse(newCourse);
        assertNotNull(createdCourse);
        assertEquals(newCourse.getCourseId(), createdCourse.getCourseId());
        assertEquals(newCourse.getCourseName(), createdCourse.getCourseName());
        assertEquals(newCourse.getBatch().getBatchId(), createdCourse.getBatch().getBatchId());
    }

    @Test
    void createCourses() {
        Course newCourse1 = Course.builder()
                .courseId("C1")
                .batch(Batch.builder().batchId("B1").build())
                .courseName("cName")
                .description("cDescription")
                .build();
        Course newCourse2 = Course.builder()
                .courseId("C2")
                .batch(Batch.builder().batchId("B1").build())
                .courseName("cName")
                .description("cDescription")
                .build();
        List<Course> newCoursesList = Arrays.asList(newCourse1, newCourse2);
        when(courseRepository.saveAll(newCoursesList)).thenReturn(newCoursesList);
        List<Course> createdCoursesList = courseService.createCourses(newCoursesList);
        assertNotNull(createdCoursesList);
        assertArrayEquals(newCoursesList.toArray(), createdCoursesList.toArray());
        verify(courseRepository).saveAll(any(List.class));
    }

    @Test
    void deleteCourseByCourseIdWhenCourseExists() {
        String courseId = "C1";
        Course course = Course.builder()
                .courseId(courseId)
                .batch(Batch.builder().batchId("B1").build())
                .courseName("cName")
                .build();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        String actualResult = courseService.deleteCourseByCourseId(courseId);
        assertEquals("Course ID : " + courseId + " has been deleted successfully!", actualResult);
        verify(courseRepository).deleteById(courseId);
    }

    @Test
    void deleteCourseByCourseIdWhenCourseDoesNotExist() {
        String courseId = "C1";
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        String actualResult = courseService.deleteCourseByCourseId(courseId);
        assertEquals("Course ID : " + courseId + " does not exist.", actualResult);
        verify(courseRepository, never()).deleteById(courseId);
    }

    @Test
    void updateCourseWhenCourseExists() {
        Course existsCourse = Course.builder().courseId("C1").courseName("Cname").build();
        Course updateCourse = Course.builder().courseId("C1").courseName("update").build();
        when(courseRepository.findById("C1")).thenReturn(Optional.ofNullable(existsCourse));
        when(courseRepository.save(updateCourse)).thenReturn(updateCourse);
        Course actualUpdatedCourse = courseService.updateCourse(updateCourse);
        assertNotNull(actualUpdatedCourse);
        assertEquals(updateCourse.getCourseName(), actualUpdatedCourse.getCourseName());
    }

    @Test
    void updateCourseWhenCourseIdIsNull() {
        Course nullCourse = Course.builder().courseId(null).build();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.updateCourse(nullCourse);
        });
        assertEquals("Invalid course data or course ID.", exception.getMessage());
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void updateCourseWhenCourseDoesNotExist() {
        Course courseDoesNotExist = Course.builder().courseId("C1").build();
        when(courseRepository.findById("C1")).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            courseService.updateCourse(courseDoesNotExist);
        });
        assertEquals("Course not found.", exception.getMessage());
        verify(courseRepository).findById("C1");
        verify(courseRepository, never()).save(any(Course.class));
    }

//    @Test
//    void saveCoursesFromExcel() throws IOException {
//        MockMultipartFile mockMultipartFile = new MockMultipartFile(
//                "test.xlsx", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[0]
//        );
//        List<Course> mockCourses = new ArrayList<>();
//        Course course1 = Course.builder().courseId("C1").courseName("C1 course").build();
//        Course course2 = Course.builder().courseId("C2").courseName("C2 course").build();
//        mockCourses.add(course1);
//        mockCourses.add(course2);
//        when(ExcelParser.parseCourseExcel(Mockito.any(InputStream.class))).thenReturn(mockCourses);
//        when(courseRepository.saveAll(Mockito.anyList())).thenReturn(mockCourses);
//        String result = courseService.saveCoursesFromExcel(mockMultipartFile);
//        verify(excelParser).parseCourseExcel(Mockito.any(InputStream.class));
//        verify(courseRepository).saveAll(mockCourses);
//        assertEquals("Courses saved!", result);
//
//    }
}
