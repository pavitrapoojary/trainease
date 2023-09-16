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
                        batchCoursesProgress.getEmailId().equals(emailId))
                .collect(Collectors.toList());
    }

    @Override
    public String updateCourseProgress(CourseProgress updatedCourseProgress) {
        Optional<CourseProgress> courseProgressExistsOptional = courseProgressRepository.findByEmailIdAndCourseId(updatedCourseProgress.getEmailId(), updatedCourseProgress.getCourseId());
        if (courseProgressExistsOptional.isPresent()) {
            CourseProgress courseProgressExists = courseProgressExistsOptional.get();
            courseProgressExists.setStatus(updatedCourseProgress.getStatus());
            courseProgressExists.setFeedback(updatedCourseProgress.getFeedback());
            courseProgressExists.setActualStartDate(updatedCourseProgress.getActualStartDate());
            courseProgressExists.setActualEndDate(updatedCourseProgress.getActualEndDate());
            courseProgressRepository.save(courseProgressExists);
            return "Progress for Course ID : " + courseProgressExists.getCourseId() + " has been updated!";
        }
        return "Course ID : " + updatedCourseProgress.getCourseId() + " does not exist for Trainee Email ID : " + updatedCourseProgress.getEmailId();
    }

}
