package com.trainease.controller;

import com.trainease.entity.BatchCourses;
import com.trainease.entity.Course;
import com.trainease.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/{batchId}/courses")
    public List<Course> getCoursesByBatchId(@PathVariable String batchId) {
        return this.courseService.getCoursesByBatchId(batchId);
    }

    @GetMapping("/batchCourses")
    public List<BatchCourses> getAllBatchDetails() {
        return this.courseService.getAllBatchDetails();
    }

    @PostMapping("/course")
    public Course createCourse(@RequestBody Course course) {
        return this.courseService.createCourse(course);
    }

    @DeleteMapping("/course/{courseId}")
    public String deleteCourseByCourseId(@PathVariable String courseId){
        return this.courseService.deleteCourseByCourseId(courseId);
    }

}
