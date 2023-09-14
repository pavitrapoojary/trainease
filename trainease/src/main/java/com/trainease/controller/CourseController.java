package com.trainease.controller;

import com.trainease.entity.BatchWiseCourses;
import com.trainease.entity.Course;
import com.trainease.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course course) {
        return this.courseService.createCourse(course);
    }

    @DeleteMapping("/courses/{courseId}")
    public String deleteCourseByCourseId(@PathVariable String courseId) {
        return this.courseService.deleteCourseByCourseId(courseId);
    }

}
