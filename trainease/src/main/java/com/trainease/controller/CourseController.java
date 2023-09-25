package com.trainease.controller;

import com.trainease.entity.Course;
import com.trainease.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return this.courseService.getAllCourses();
    }

    @GetMapping("/{batchId}/courses")
    public List<Course> getCoursesByBatchId(@PathVariable String batchId) {
        return this.courseService.getCoursesByBatchId(batchId);
    }

    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course course) {
        return this.courseService.createCourse(course);
    }

    @PostMapping("/courses/saveAll")
    public List<Course> createCourses(@RequestBody List<Course> courses) {
        return this.courseService.createCourses(courses);
    }

    @PutMapping("/courses")
    public Course updateCourse(@RequestBody Course updatedCourse){
        return this.courseService.updateCourse(updatedCourse);
    }

    @DeleteMapping("/courses/{courseId}")
    public String deleteCourseByCourseId(@PathVariable String courseId) {
        return this.courseService.deleteCourseByCourseId(courseId);
    }

    @PostMapping("/courses/saveExcel")
    public List<Course> saveCoursesFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return courseService.saveCoursesFromExcel(file);
    }

}
