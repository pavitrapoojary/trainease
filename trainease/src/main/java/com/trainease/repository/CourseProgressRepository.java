package com.trainease.repository;

import com.trainease.entity.CourseProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseProgressRepository extends MongoRepository<CourseProgress, Integer> {
    Optional<CourseProgress> findByEmailIdAndCourseId(String emailId, String courseId);
}
