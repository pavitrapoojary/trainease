package com.trainease.service;

import com.trainease.entity.BatchWiseCourses;
import com.trainease.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCoursesByBatchId(String batchId);

    List<BatchWiseCourses> getAllBatchWiseCourses();

    Course createCourse(Course course);

    String deleteCourseByCourseId(String courseId);
}
