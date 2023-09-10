package com.trainease.service;

import com.trainease.entity.User;
import com.trainease.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl(userRepository);

    @Test
    void getAllUsersNoParam() {
        User user1 = User.builder().emailId("test1@gmail.com").name("test1").role("ADMIN").build();
        User user2 = User.builder().emailId("test2@gmail.com").name("test2").role("TRAINEE").batchId("B1").build();
        User user3 = User.builder().emailId("test3@gmail.com").name("test3").role("TRAINEE").batchId("B2").build();
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers(null, null);
        assertArrayEquals(expectedUserList.toArray(), actualUserList.toArray());
    }

    @Test
    void getAllUsersByRole() {
        User user1 = User.builder().emailId("test1@gmail.com").name("test1").role("ADMIN").build();
        User user2 = User.builder().emailId("test2@gmail.com").name("test2").role("TRAINEE").batchId("B1").build();
        User user3 = User.builder().emailId("test3@gmail.com").name("test3").role("TRAINEE").batchId("B2").build();
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers("ADMIN", null);
        assertEquals(1, actualUserList.size());
        assertTrue(actualUserList.contains(user1));
    }

    @Test
    void getAllUsersByBatchId() {
        User user1 = User.builder().emailId("test1@gmail.com").name("test1").role("ADMIN").build();
        User user2 = User.builder().emailId("test2@gmail.com").name("test2").role("TRAINEE").batchId("B1").build();
        User user3 = User.builder().emailId("test3@gmail.com").name("test3").role("TRAINEE").batchId("B2").build();
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers(null, "B1");
        assertEquals(1, actualUserList.size());
        assertTrue(actualUserList.contains(user2));
    }

    @Test
    void getAllUsersByRoleAndBatchId() {
        User user1 = User.builder().emailId("test1@gmail.com").name("test1").role("ADMIN").build();
        User user2 = User.builder().emailId("test2@gmail.com").name("test2").role("TRAINEE").batchId("B1").build();
        User user3 = User.builder().emailId("test3@gmail.com").name("test3").role("TRAINEE").batchId("B2").build();
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers("TRAINEE", "B2");
        assertEquals(1, actualUserList.size());
        assertTrue(actualUserList.contains(user3));
    }

    @Test
    void getUserByEmailIdUserExists() {
        User expectedUser = User.builder().emailId("test@gmail.com").name("test").role("ADMIN").build();
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
    void createUser() {
        User newUser = User.builder().emailId("p@gmail.com").name("test").role("ADMIN").build();
        when(userRepository.save(newUser)).thenReturn(newUser);
        User createdUser = userService.createUser(newUser);
        assertNotNull(createdUser);
        assertEquals(newUser.getEmailId(), createdUser.getEmailId());
        assertEquals(newUser.getName(), createdUser.getName());
        assertEquals(newUser.getRole(), createdUser.getRole());
    }

    @Test
    void updateUserWhenUserExists() {
        String emailId = "test@gmail.com";
        User existingUser = User.builder().emailId(emailId).name("test").role("ADMIN").build();
        User updatedUser = User.builder().emailId(emailId).name("testUpdated").role("ADMIN").build();
        when(userRepository.findById(emailId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        String actualResult = userService.updateUser(updatedUser);
        assertEquals("User " + emailId + " updated successfully!", actualResult);
        verify(userRepository).findById(emailId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUserWhenUserDoesNotExist() {
        String emailId = "test@gmail.com";
        User updatedUser = User.builder().emailId(emailId).name("testUpdated").role("ADMIN").build();
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());
        String actualResult = userService.updateUser(updatedUser);
        assertEquals("User " + emailId + " does not exist.", actualResult);
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    void deleteUserByEmailIdWhenUserExists() {
        String emailId = "test@gmail.com";
        User user = User.builder().emailId(emailId).name("test").role("ADMIN").build();
        when(userRepository.findById(emailId)).thenReturn(Optional.of(user));
        String actualResult = userService.deleteUserByEmailId(emailId);
        assertEquals("User " + emailId + " deleted successfully!", actualResult);
        verify(userRepository).deleteById(emailId);
    }

    @Test
    void deleteUserByEmailIdWhenUserDoesNotExist() {
        String emailId = "test@gmail.com";
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());
        String actualResult = userService.deleteUserByEmailId(emailId);
        assertEquals("User " + emailId + " does not exist.", actualResult);
        verify(userRepository, never()).deleteById(emailId);
    }

}
