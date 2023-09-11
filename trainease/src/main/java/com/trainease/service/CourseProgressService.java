package com.trainease.service;

import com.trainease.entity.CourseProgress;

import java.util.List;

public interface CourseProgressService {
    List<CourseProgress> getCoursesProgressByTraineeEmailId(String emailId);

    String updateCourseProgress(String emailId, Integer progressId, CourseProgress courseProgress);
}
