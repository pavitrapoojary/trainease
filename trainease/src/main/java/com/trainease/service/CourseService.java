package com.trainease.service;

import com.trainease.entity.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    List<Course> getCoursesByBatchId(String batchId);

    Course createCourse(Course course);

    Course updateCourse(Course course);

    List<Course> createCourses(List<Course> courses);


    String deleteCourseByCourseId(String courseId);

    List<Course> saveCoursesFromExcel(MultipartFile excelFile) throws IOException;

}
