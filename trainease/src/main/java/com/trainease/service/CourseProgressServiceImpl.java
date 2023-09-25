package com.trainease.service;

import com.trainease.entity.CourseProgress;
import com.trainease.repository.CourseProgressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseProgressServiceImpl implements CourseProgressService {
    private CourseProgressRepository courseProgressRepository;

    @Override
    public List<CourseProgress> getCoursesProgressByTraineeEmailId(String emailId) {
        return courseProgressRepository.findAll()
                .stream()
                .filter(batchCoursesProgress ->
                        batchCoursesProgress.getUser().getEmailId().equals(emailId))
                .collect(Collectors.toList());
    }

    @Override
    public CourseProgress getCourseProgressByTraineeEmailIdAndCourseId(String emailId, String courseId) {
        return courseProgressRepository.findByEmailIdAndCourseId(emailId, courseId).orElse(null);
    }

    @Override
    public CourseProgress updateCourseProgress(CourseProgress updatedCourseProgress) {
        Optional<CourseProgress> courseProgressExistsOptional = courseProgressRepository.findByEmailIdAndCourseId(updatedCourseProgress.getUser().getEmailId(), updatedCourseProgress.getCourse().getCourseId());
        CourseProgress courseProgressExists = courseProgressExistsOptional.get();
        courseProgressExists.setStatus(updatedCourseProgress.getStatus());
        courseProgressExists.setFeedback(updatedCourseProgress.getFeedback());
        courseProgressExists.setActualStartDate(updatedCourseProgress.getActualStartDate());
        courseProgressExists.setActualEndDate(updatedCourseProgress.getActualEndDate());
        return courseProgressRepository.save(courseProgressExists);
    }

}
