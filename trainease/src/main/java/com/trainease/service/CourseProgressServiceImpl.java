package com.trainease.service;

import com.trainease.entity.CourseProgress;
import com.trainease.repository.CourseProgressRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressServiceImpl implements CourseProgressService {
    private CourseProgressRepository courseProgressRepository;

//    public CourseProgressServiceImpl(CourseProgressRepository courseProgressRepository) {
//        this.courseProgressRepository = courseProgressRepository;
//    }

    @Override
    public List<CourseProgress> getCoursesProgressByTraineeEmailId(String emailId) {
        return courseProgressRepository.findAll()
                .stream()
                .filter(batchCoursesProgress ->
                        batchCoursesProgress.getEmailId().equals(emailId))
                .collect(Collectors.toList());
    }

    @Override
    public String updateCourseProgress(String emailId, Integer progressId, CourseProgress courseProgress) {
        Optional<CourseProgress> existingBatchCourseProgressOptional = courseProgressRepository.findById(progressId);
        if (existingBatchCourseProgressOptional.isPresent()) {
            CourseProgress existingCourseProgress = existingBatchCourseProgressOptional.get();
            existingCourseProgress.setStatus(courseProgress.getStatus());
            existingCourseProgress.setFeedback(courseProgress.getFeedback());
            existingCourseProgress.setActualStartDate(courseProgress.getActualStartDate());
            existingCourseProgress.setActualEndDate(courseProgress.getActualEndDate());
            courseProgressRepository.save(existingCourseProgress);
            return "Course progress for batchId:" + courseProgress.getBatchId() + " & courseId:" + courseProgress.getCourseId() + " has been updated successfully!";
        } else {
            return "Given progressId:" + progressId + " does not exist.";
        }
    }
}
