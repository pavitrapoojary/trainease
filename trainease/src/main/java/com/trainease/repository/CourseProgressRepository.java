package com.trainease.repository;

import com.trainease.entity.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Integer> {
}
