package com.trainease.repository;

import com.trainease.entity.User;
import com.trainease.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u " +
            "WHERE u.batch.batchId = :batchId ")
    List<User> findByBatchId(@Param("batchId") String batchId);

    @Query("SELECT u FROM User u " +
            "WHERE u.role = :role ")
    List<User>findByUserRole(@Param("role")UserRole role);
}
