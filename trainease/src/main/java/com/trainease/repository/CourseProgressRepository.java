package com.trainease.repository;

import com.trainease.entity.CourseProgress;
import com.trainease.dto.CourseStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Integer> {
    @Query("SELECT cp FROM CourseProgress cp " +
            "WHERE cp.user.emailId = :emailId " +
            "AND cp.course.courseId = :courseId")
    Optional<CourseProgress> findByEmailIdAndCourseId(@Param("emailId") String emailId, @Param("courseId") String courseId);

    @Query("SELECT cp FROM CourseProgress cp " +
            "WHERE cp.course.courseId = :courseId")
    List<CourseProgress> findByCourseId(@Param("courseId") String courseId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CourseProgress cp " +
            "WHERE cp.user.emailId = :emailId")
    void deleteCourseProgressByEmailId(@Param("emailId") String emailId);

    Long countByCourse_CourseIdAndStatus(String courseId, CourseStatus status);

    Long countByUser_EmailIdAndStatus(String emailId, CourseStatus courseStatus);

    @Query("SELECT cp FROM CourseProgress cp " +
            "WHERE cp.batch.batchId = :batchId ")
    List<CourseProgress> findBatchProgressByBatchId(@Param("batchId") String batchId);


}
