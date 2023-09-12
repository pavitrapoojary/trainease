package com.trainease.service;

import com.trainease.entity.*;
import com.trainease.repository.CourseProgressRepository;
import com.trainease.repository.CourseRepository;
import com.trainease.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private CourseProgressRepository courseProgressRepository;

    private CourseRepository courseRepository;

    @Override
    public List<User> getAllUsers(UserRole role, String batchId) {
        if (role != null && role.equals(UserRole.TRAINEE) && batchId != null) {
            return userRepository.findAll()
                    .stream()
                    .filter(user -> user.getRole().equals(role) && user.getBatchId().equals(batchId))
                    .collect(Collectors.toList());
        }
        if (role != null) {
            return userRepository.findAll()
                    .stream()
                    .filter(user -> user.getRole().equals(role))
                    .collect(Collectors.toList());
        }

        if (batchId != null) {
            return userRepository.findAll()
                    .stream()
                    .filter(user -> user.getBatchId() != null && user.getBatchId().equals(batchId))
                    .collect(Collectors.toList());
        }

        return userRepository.findAll();
    }

    @Override
    public User getUserByEmailId(String emailId) {
        return userRepository.findById(emailId).orElse(null);
    }

    @Override
    public User createUser(User user) {
        if (user.getRole().equals(UserRole.TRAINEE)) {
            List<Course> allCoursesForGivenBatchId = courseRepository.findAll()
                    .stream()
                    .filter(course -> course.getBatchId().equals(user.getBatchId()))
                    .toList();
            for (Course currentCourse : allCoursesForGivenBatchId) {
                CourseProgress courseProgress = CourseProgress.builder()
                        .emailId(user.getEmailId()).batchId(user.getBatchId())
                        .courseId(currentCourse.getCourseId())
                        .courseName(currentCourse.getCourseName())
                        .description(currentCourse.getDescription())
                        .link(currentCourse.getLink())
                        .durationInHours(currentCourse.getDurationInHours())
                        .estimatedStartDate(currentCourse.getEstimatedStartDate())
                        .estimatedEndDate(currentCourse.getEstimatedEndDate())
                        .subjectMatterExpert(currentCourse.getSubjectMatterExpert())
                        .status(CourseStatus.TO_BE_STARTED)
                        .feedback("NA")
                        .build();
                courseProgressRepository.save(courseProgress);
            }
        }
        return userRepository.save(user);
    }

    @Override
    public String updateUser(User user) {
        //future scope
        Optional<User> existingUserOptional = userRepository.findById(user.getEmailId());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(user.getName());
            existingUser.setRole(user.getRole());
            existingUser.setBatchId(user.getBatchId());
            userRepository.save(existingUser);
            return "User " + user.getEmailId() + " updated successfully!";
        }
        return "User " + user.getEmailId() + " does not exist.";
    }

    @Override
    public String deleteUserByEmailId(String emailId) {
        if (userRepository.findById(emailId).isPresent()) {
            userRepository.deleteById(emailId);
            return "User " + emailId + " deleted successfully!";
        }
        return "User " + emailId + " does not exist.";
    }

}
