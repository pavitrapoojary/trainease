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

    @GetMapping("/{emailId}/courses/progress")
    public List<CourseProgress> getCoursesProgressByTraineeEmailId(@PathVariable String emailId) {
        return this.courseProgressService.getCoursesProgressByTraineeEmailId(emailId);
    }

    @PutMapping("/{emailId}/courses/progress")
    public String updateCourseProgress(@PathVariable String emailId,
                                       @RequestParam Integer progressId,
                                       @RequestBody CourseProgress courseProgress) {
        return this.courseProgressService.updateCourseProgress(emailId, progressId, courseProgress);
    }

}
