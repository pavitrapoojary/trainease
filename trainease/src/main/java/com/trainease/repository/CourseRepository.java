package com.trainease.repository;

import com.trainease.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Long countByBatch_BatchId(String batchId);
}
