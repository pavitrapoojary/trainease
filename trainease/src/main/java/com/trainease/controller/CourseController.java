package com.trainease.controller;

import com.trainease.entity.BatchWiseCourses;
import com.trainease.entity.Course;
import com.trainease.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/{batchId}/courses")
    public List<Course> getCoursesByBatchId(@PathVariable String batchId) {
        return this.courseService.getCoursesByBatchId(batchId);
    }

    @GetMapping("/batch/courses")
    public List<BatchWiseCourses> getAllBatchWiseCourses() {
        return this.courseService.getAllBatchWiseCourses();
    }

    @PostMapping("/course")
    public Course createCourse(@RequestBody Course course) {
        return this.courseService.createCourse(course);
    }

    @DeleteMapping("/course/{courseId}")
    public String deleteCourseByCourseId(@PathVariable String courseId) {
        return this.courseService.deleteCourseByCourseId(courseId);
    }

}
