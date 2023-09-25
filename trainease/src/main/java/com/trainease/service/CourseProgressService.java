package com.trainease.service;

import com.trainease.entity.CourseProgress;

import java.util.List;

public interface CourseProgressService {
    List<CourseProgress> getCoursesProgressByTraineeEmailId(String emailId);
    CourseProgress getCourseProgressByTraineeEmailIdAndCourseId(String emailId, String courseId);
    CourseProgress updateCourseProgress(CourseProgress courseProgress);
}
