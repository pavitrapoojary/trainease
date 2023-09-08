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
    void getAllUserDetailsNoOptionalParam() {
        User user1 = new User("test1@gmail.com", "test1", "ADMIN", null);
        User user2 = new User("test2@gmail.com", "test2", "TRAINEE", "B1");
        User user3 = new User("test3@gmail.com", "test3", "TRAINEE", "B2");
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers(null, null);
        assertArrayEquals(expectedUserList.toArray(), actualUserList.toArray());
    }

    @Test
    void getAllUserDetailsByRole() {
        User user1 = new User("test1@gmail.com", "test1", "ADMIN", null);
        User user2 = new User("test2@gmail.com", "test2", "TRAINEE", "B1");
        User user3 = new User("test3@gmail.com", "test3", "TRAINEE", "B2");
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers("ADMIN", null);
        assertEquals(1, actualUserList.size());
        assertTrue(actualUserList.contains(user1));
    }

    @Test
    void getAllUserDetailsByBatchId() {
        User user1 = new User("test1@gmail.com", "test1", "ADMIN", null);
        User user2 = new User("test2@gmail.com", "test2", "TRAINEE", "B1");
        User user3 = new User("test3@gmail.com", "test3", "TRAINEE", "B2");
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers(null, "B1");
        assertEquals(1, actualUserList.size());
        assertTrue(actualUserList.contains(user2));
    }

    @Test
    void getAllUserDetailsByRoleAndBatchId() {
        User user1 = new User("test1@gmail.com", "test1", "ADMIN", null);
        User user2 = new User("test2@gmail.com", "test2", "TRAINEE", "B1");
        User user3 = new User("test3@gmail.com", "test3", "TRAINEE", "B2");
        List<User> expectedUserList = Arrays.asList(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers("TRAINEE", "B2");
        assertEquals(1, actualUserList.size());
        assertTrue(actualUserList.contains(user3));
    }

    @Test
    void getUserByEmailIdUserExists() {
        User expectedUser = new User("test@gmail.com", "test", "ADMIN", null);
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
        User newUser = new User("p@gmail.com", "test", "ADMIN", null);
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
        User existingUser = new User(emailId, "test", "ADMIN", null);
        User updatedUser = new User(emailId, "testUpdated", "ADMIN", null);
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
        User updatedUser = new User(emailId, "testUpdated", "ADMIN", null);
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());
        String actualResult = userService.updateUser(updatedUser);
        assertEquals("User " + emailId + " does not exist.", actualResult);
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    void deleteUserWhenUserExists() {
        String emailId = "test@gmail.com";
        User user = new User(emailId, "test", "ADMIN", null);
        when(userRepository.findById(emailId)).thenReturn(Optional.of(user));
        String actualResult = userService.deleteUser(emailId);
        assertEquals("User " + emailId + " deleted successfully!", actualResult);
        verify(userRepository).deleteById(emailId);
    }

    @Test
    void deleteUserWhenUserDoesNotExist() {
        String emailId = "test@gmail.com";
        when(userRepository.findById(emailId)).thenReturn(Optional.empty());
        String actualResult = userService.deleteUser(emailId);
        assertEquals("User " + emailId + " does not exist.", actualResult);
        verify(userRepository, never()).deleteById(emailId);
    }

}
