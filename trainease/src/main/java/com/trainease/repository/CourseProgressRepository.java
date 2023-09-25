package com.trainease.repository;

import com.trainease.entity.CourseProgress;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Integer> {
    @Query("SELECT cp FROM CourseProgress cp " +
            "WHERE cp.user.emailId = :emailId " +
            "AND cp.course.courseId = :courseId")
    Optional<CourseProgress> findByEmailIdAndCourseId(@Param("emailId") String emailId, @Param("courseId") String courseId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CourseProgress cp " +
            "WHERE cp.user.emailId = :emailId")
    void deleteCourseProgressByEmailId(@Param("emailId") String emailId);
}
