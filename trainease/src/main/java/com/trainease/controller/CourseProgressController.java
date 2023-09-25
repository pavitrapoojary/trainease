package com.trainease.controller;

import com.trainease.entity.CourseProgress;
import com.trainease.service.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CourseProgressController {
    @Autowired
    CourseProgressService courseProgressService;

    @GetMapping("/{emailId}/progress")
    public List<CourseProgress> getCoursesProgressByTraineeEmailId(@PathVariable String emailId) {
        return this.courseProgressService.getCoursesProgressByTraineeEmailId(emailId);
    }

    @GetMapping("/progress/{emailId}/{courseId}")
    public CourseProgress getCourseProgressByTraineeEmailIdAndCourseId(@PathVariable String emailId, @PathVariable String courseId) {
        return this.courseProgressService.getCourseProgressByTraineeEmailIdAndCourseId(emailId,courseId);
    }

    @PutMapping("/progress")
    public CourseProgress updateCourseProgress(@RequestBody CourseProgress courseProgress) {
        return this.courseProgressService.updateCourseProgress(courseProgress);
    }

}
