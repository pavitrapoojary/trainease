package com.trainease.repository;

import com.trainease.entity.CourseProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseProgressRepository extends MongoRepository<CourseProgress, Integer> {
}
