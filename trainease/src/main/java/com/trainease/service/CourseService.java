package com.trainease.service;

import com.trainease.entity.BatchCourses;
import com.trainease.entity.Course;
import java.util.List;

public interface CourseService {
    List<Course> getCoursesByBatchId(String batchId);

    List<BatchCourses> getAllBatchDetails();

    Course createCourse(Course course);
    String deleteCourseByCourseId(String courseId);
}
