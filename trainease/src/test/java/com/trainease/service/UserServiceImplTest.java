package com.trainease.service;

import com.trainease.entity.*;
import com.trainease.repository.CourseProgressRepository;
import com.trainease.repository.CourseRepository;
import com.trainease.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseProgressRepository courseProgressRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void getAllUsersNoParam() {
        User user1 = User.builder().emailId("test1@gmail.com").name("test1").role(UserRole.ADMIN).build();
        User user2 = User.builder().emailId("test2@gmail.com").name("test2").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B1").build()).build();
        User user3 = User.builder().emailId("test3@gmail.com").name("test3").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B2").build()).build();
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers(null, null);
        assertArrayEquals(expectedUserList.toArray(), actualUserList.toArray());
    }

    @Test
    void getAllUsersByRole() {
        User user1 = User.builder().emailId("test1@gmail.com").name("test1").role(UserRole.ADMIN).build();
        List<User> expectedUserList = Arrays.asList(user1);
        when(userRepository.findByUserRole(UserRole.ADMIN)).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers(UserRole.ADMIN, null);
        assertEquals(1, actualUserList.size());
        assertTrue(actualUserList.contains(user1));
    }

    @Test
    void getAllUsersByBatchId() {
        User user2 = User.builder().emailId("test2@gmail.com").name("test2").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B1").build()).build();
        List<User> expectedUserList = Arrays.asList(user2);
        when(userRepository.findByBatchId("B1")).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers(null, "B1");
        assertEquals(1, actualUserList.size());
        assertTrue(actualUserList.contains(user2));
    }

    @Test
    void getAllUsersByRoleAndBatchId() {
        User user1 = User.builder().emailId("test1@gmail.com").name("test1").role(UserRole.ADMIN).build();
        User user2 = User.builder().emailId("test2@gmail.com").name("test2").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B1").build()).build();
        User user3 = User.builder().emailId("test3@gmail.com").name("test3").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B2").build()).build();
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers(UserRole.TRAINEE, "B2");
        assertEquals(1, actualUserList.size());
        assertTrue(actualUserList.contains(user3));
    }

    @Test
    void getUserByEmailIdUserExists() {
        User expectedUser = User.builder().emailId("test@gmail.com").name("test").role(UserRole.ADMIN).build();
        when(userRepository.findById("test@gmail.com")).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.getUserByEmailId("test@gmail.com");
        assertNotNull(actualUser);
        assertEquals(expectedUser.getEmailId(), actualUser.getEmailId());
        assertEquals(expectedUser.getName(), actualUser.getName());
    }

    @Test
    void getUserByEmailIdUserDoesNotExist() {
        String emailId = "test@gmail.com";
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());
        User actualUser = userService.getUserByEmailId(emailId);
        assertNull(actualUser);
    }

    @Test
    void createAdminUser() {
        User newAdminUser = User.builder().emailId("admin@gmail.com").name("demo admin").role(UserRole.ADMIN).build();
        when(userRepository.save(newAdminUser)).thenReturn(newAdminUser);
        User createdAdminUser = userService.createUser(newAdminUser);
        assertNotNull(createdAdminUser);
        assertEquals(newAdminUser.getEmailId(), createdAdminUser.getEmailId());
        assertEquals(newAdminUser.getName(), createdAdminUser.getName());
        assertEquals(newAdminUser.getRole(), createdAdminUser.getRole());
    }

    @Test
    void createSMEUser() {
        User newSmeUser = User.builder().emailId("admin@gmail.com").name("demo admin").role(UserRole.SME).build();
        when(userRepository.save(newSmeUser)).thenReturn(newSmeUser);
        User createdSmeUser = userService.createUser(newSmeUser);
        assertNotNull(createdSmeUser);
        assertEquals(newSmeUser.getEmailId(), createdSmeUser.getEmailId());
        assertEquals(newSmeUser.getName(), createdSmeUser.getName());
        assertEquals(newSmeUser.getRole(), createdSmeUser.getRole());
    }

    @Test
    void createTraineeUser() {
        User newTraineeUser = User.builder().emailId("trainee@gmail.com")
                .name("demo trainee").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B1").build()).build();

        Course course = Course.builder().courseId("C1").batch(Batch.builder().batchId("B1").build()).courseName("cName").build();
        Course course1 = Course.builder().courseId("C2").batch(Batch.builder().batchId("B1").build()).courseName("cName").build();
        Course course2 = Course.builder().courseId("C3").batch(Batch.builder().batchId("B2").build()).courseName("cName").build();
        List<Course> courseList = Arrays.asList(course, course1, course2);

        when(courseRepository.findAll()).thenReturn(courseList);
        when(userRepository.save(newTraineeUser)).thenReturn(newTraineeUser);

        User createdTraineeUser = userService.createUser(newTraineeUser);

        assertNotNull(createdTraineeUser);
        assertEquals(newTraineeUser.getEmailId(), createdTraineeUser.getEmailId());
        assertEquals(newTraineeUser.getName(), createdTraineeUser.getName());
        assertEquals(newTraineeUser.getRole(), createdTraineeUser.getRole());

        verify(userRepository).save(newTraineeUser);
        verify(courseRepository).findAll();
        verify(courseProgressRepository, times(2)).save(any(CourseProgress.class));
    }

    @Test
    void updateUserWhenUserExists() {
        String emailId = "test@gmail.com";
        User existingUser = User.builder().emailId(emailId).name("test").role(UserRole.ADMIN).build();
        User updatedUser = User.builder().emailId(emailId).name("testUpdated").role(UserRole.ADMIN).build();
        when(userRepository.findById(emailId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        User actualUpdatedUser = userService.updateUser(updatedUser);
        assertEquals(updatedUser.getName(), actualUpdatedUser.getName());
        verify(userRepository).findById(emailId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUserWhenUserDoesNotExist() {
        String emailId = "test@gmail.com";
        User updatedUser = User.builder().emailId(emailId).name("testUpdated").role(UserRole.ADMIN).build();
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUser(updatedUser);
        });
        assertEquals("User not found.", exception.getMessage());
        verify(userRepository).findById(emailId);
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    void updateUserWhenUserISNull() {
        User nullUser = null;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(nullUser);
        });
        assertEquals("Invalid user data or user email id.", exception.getMessage());
        verify(userRepository, never()).findById(any(String.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUserByEmailIdWhenUserExists() {
        String emailId = "test@gmail.com";
        User user = User.builder().emailId(emailId).name("test").role(UserRole.TRAINEE).batch(Batch.builder().batchId("B1").build()).build();
        when(userRepository.findById(emailId)).thenReturn(Optional.of(user));
        String actualResult = userService.deleteUserByEmailId(emailId);
        assertEquals("User " + emailId + " deleted successfully!", actualResult);
        verify(userRepository).deleteById(emailId);
        verify(courseProgressRepository).deleteCourseProgressByEmailId(emailId);
    }

    @Test
    void deleteUserByEmailIdWhenUserDoesNotExist() {
        String emailId = "test@gmail.com";
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUserByEmailId(emailId);
        });
        assertEquals("User " + emailId + " does not exist.", exception.getMessage());
        verify(userRepository).findById(emailId);
        verify(userRepository, never()).deleteById(emailId);
    }

    @Test
    void deleteUserByEmailIdWhenEmailIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUserByEmailId(null);
        });
        assertEquals("Invalid emailId.", exception.getMessage());
        verify(userRepository, never()).findById(any(String.class));
        verify(userRepository, never()).deleteById(any(String.class));
    }

}
