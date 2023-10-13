package com.trainease.service;

import com.trainease.dto.CourseStatus;
import com.trainease.dto.UserLoginData;
import com.trainease.dto.UserRole;
import com.trainease.helper.ExcelParser;
import com.trainease.entity.*;
import com.trainease.repository.CourseProgressRepository;
import com.trainease.repository.CourseRepository;
import com.trainease.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.trainease.helper.S3FileUploader.uploadFileToS3;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CourseProgressRepository courseProgressRepository;
    private CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers(UserRole role, String batchId) {
        if (role != null && role.equals(UserRole.TRAINEE) && batchId != null) {
            return userRepository.findAll()
                    .stream()
                    .filter(user -> user.getRole().equals(role) && user.getBatch().getBatchId().equals(batchId))
                    .collect(Collectors.toList());
        }
        if (role != null) {
            return userRepository.findByUserRole(role);
        }

        if (batchId != null) {
            return userRepository.findByBatchId(batchId);
        }

        return userRepository.findAll();
    }

    @Override
    public User getUserByEmailId(String emailId) {
        return userRepository.findById(emailId).orElse(null);
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findById(user.getEmailId()).isEmpty()) {
            if (user.getRole().equals(UserRole.TRAINEE)) {
                List<Course> allCoursesForGivenBatchId = courseRepository.findAll()
                        .stream()
                        .filter(course -> course.getBatch().getBatchId().equals(user.getBatch().getBatchId()))
                        .toList();
                String hashedPassword = passwordEncoder.encode(user.getEmailId());
                user.setPassword(hashedPassword);
                userRepository.save(user);
                for (Course currentCourse : allCoursesForGivenBatchId) {
                    CourseProgress courseProgress = CourseProgress.builder()
                            .user(user)
                            .batch(user.getBatch())
                            .course(currentCourse)
                            .status(CourseStatus.TO_BE_STARTED)
                            .build();
                    courseProgressRepository.save(courseProgress);
                }
            } else if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.SME)) {
                user.setBatch(null);
                String hashedPassword = passwordEncoder.encode(user.getEmailId());
                user.setPassword(hashedPassword);
                userRepository.save(user);
            }
            return user;
        }
        throw new IllegalArgumentException("User already exists with the given user email ID.");
    }

    @Override
    public User updateUser(User updatedUser) {
        if (updatedUser == null || updatedUser.getEmailId() == null) {
            throw new IllegalArgumentException("Invalid user data or user email id.");
        }
        Optional<User> existingUserOptional = userRepository.findById(updatedUser.getEmailId());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setBatch(updatedUser.getBatch());
            return userRepository.save(existingUser);
        }
        throw new EntityNotFoundException("User not found.");
    }

    @Override
    public String deleteUserByEmailId(String emailId) {
        if (emailId == null) {
            throw new IllegalArgumentException("Invalid emailId.");
        }
        if (userRepository.findById(emailId).isPresent()) {
            if (userRepository.findById(emailId).get().getRole().equals(UserRole.TRAINEE)) {
                courseProgressRepository.deleteCourseProgressByEmailId(emailId);
            }
            userRepository.deleteById(emailId);
            return "User " + emailId + " deleted successfully!";
        }
        throw new EntityNotFoundException("User " + emailId + " does not exist.");
    }

    @Override
    public List<User> saveUsersFromExcel(MultipartFile excelFile) throws IOException {
        List<User> users = ExcelParser.parseUserExcel(excelFile.getInputStream());
        for (User user : users) {
            createUser(user);
        }
        uploadFileToS3(excelFile, "users/");
        return users;
    }

    @Override
    public UserLoginData getUserRoleAndBatch(String emailId) {
        Optional<User> user = userRepository.findById(emailId);
        if (user.isPresent()) {
            User userPresent = user.get();
            return UserLoginData.builder()
                    .emailId(userPresent.getEmailId())
                    .role(userPresent.getRole())
                    .batch(userPresent.getBatch())
                    .build();
        }
        throw new UsernameNotFoundException("User not found " + emailId);
    }

    @Override
    public User getUserProfile(String emailId) {
        Optional<User> user = userRepository.findById(emailId);
        if (user.isPresent()) {
            User userExists = user.get();
            return User.builder()
                    .emailId(userExists.getEmailId())
                    .name(userExists.getName())
                    .role(userExists.getRole())
                    .batch(userExists.getBatch())
                    .build();
        }
        throw new UsernameNotFoundException("User does not exist : " + emailId);
    }

}
