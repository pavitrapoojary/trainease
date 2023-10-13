package com.trainease.controller;

import com.trainease.dto.UserLoginData;
import com.trainease.entity.Batch;
import com.trainease.entity.User;
import com.trainease.dto.UserRole;
import com.trainease.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hey test!"));
    }

    @Test
    void testGetAllUsersWithNoParams() throws Exception {
        User user1 = User.builder()
                .emailId("user1@example.com")
                .name("User 1")
                .role(UserRole.TRAINEE)
                .batch(Batch.builder().batchId("B1").build()).build();
        User user2 = User.builder()
                .emailId("user2@example.com")
                .name("User 1")
                .role(UserRole.ADMIN).build();

        List<User> userList = Arrays.asList(user1, user2);

        when(userService.getAllUsers(null, null)).thenReturn(userList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].emailId").value(user1.getEmailId()))
                .andExpect(jsonPath("$[1].emailId").value(user2.getEmailId()));

        verify(userService, times(1)).getAllUsers(null, null);
    }

    @Test
    void testGetAllUsersByRole() throws Exception {
        User user1 = User.builder().emailId("test1@gmail.com").name("test1").role(UserRole.ADMIN).build();
        List<User> expectedUserList = Collections.singletonList(user1);

        when(userService.getAllUsers(UserRole.ADMIN, null)).thenReturn(expectedUserList);

        mockMvc.perform(
                        get("/users")
                                .param("role", UserRole.ADMIN.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].emailId").value(user1.getEmailId()));

        verify(userService, times(1)).getAllUsers(UserRole.ADMIN, null);
    }

    @Test
    void getAllUsersByBatchId() throws Exception {
        User user2 = User.builder()
                .emailId("test2@gmail.com")
                .name("test2")
                .role(UserRole.TRAINEE)
                .batch(Batch.builder().batchId("B1").build()).build();

        User user3 = User.builder()
                .emailId("test3@gmail.com")
                .name("test3")
                .role(UserRole.TRAINEE)
                .batch(Batch.builder().batchId("B1").build()).build();

        List<User> expectedUserList = Arrays.asList(user2, user3);

        when(userService.getAllUsers(null, "B1")).thenReturn(expectedUserList);

        mockMvc.perform(
                        get("/users")
                                .param("batchId", "B1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].emailId").value(user2.getEmailId()))
                .andExpect(jsonPath("$[1].emailId").value(user3.getEmailId()));

        verify(userService, times(1)).getAllUsers(null, "B1");
    }

    @Test
    void testGetUserByEmailId_UserFound() throws Exception {
        String emailId = "user1@example.com";
        User user = User.builder()
                .emailId(emailId)
                .name("User 1")
                .role(UserRole.TRAINEE)
                .batch(Batch.builder().batchId("B1").build()).build();

        when(userService.getUserByEmailId(emailId)).thenReturn(user);

        mockMvc.perform(get("/users/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.emailId").value(emailId));

        verify(userService, times(1)).getUserByEmailId(emailId);
    }

    @Test
    void testCreateUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User newUser = User.builder()
                .emailId("newuser@example.com")
                .name("New User")
                .role(UserRole.TRAINEE)
                .batch(Batch.builder().batchId("B1").build()).build();

        when(userService.createUser(any(User.class))).thenReturn(newUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.emailId").value(newUser.getEmailId()));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testUpdateUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User updatedUser = User.builder()
                .emailId("user1@example.com")
                .name("Updated User")
                .role(UserRole.TRAINEE)
                .batch(Batch.builder().batchId("B1").build()).build();

        when(userService.updateUser(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated User"));

        verify(userService, times(1)).updateUser(any(User.class));
    }

    @Test
    void testDeleteUserByEmailId() throws Exception {
        String emailId = "user1@example.com";
        when(userService.deleteUserByEmailId(emailId)).thenReturn("User " + emailId + " deleted successfully!");

        mockMvc.perform(delete("/users/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(content().string("User " + emailId + " deleted successfully!"));

        verify(userService, times(1)).deleteUserByEmailId(emailId);
    }

    @Test
    void testSaveUsersFromExcel() throws Exception {
        MockMultipartFile excelFile = new MockMultipartFile("file",
                "test.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE,
                "excel content".getBytes());

        User user = User.builder()
                .emailId("user@email.com")
                .name("User")
                .role(UserRole.TRAINEE)
                .batch(Batch.builder().batchId("B1").build()).build();

        User user1 = User.builder()
                .emailId("user1@email.com")
                .name("User 1")
                .role(UserRole.TRAINEE)
                .batch(Batch.builder().batchId("B1").build()).build();

        List<User> userList = Arrays.asList(user, user1);

        when(userService.saveUsersFromExcel(any(MultipartFile.class))).thenReturn(userList);

        mockMvc.perform(multipart("/users/saveExcel").file(excelFile))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].emailId").value(user.getEmailId()))
                .andExpect(jsonPath("$[1].emailId").value(user1.getEmailId()));

        verify(userService, times(1)).saveUsersFromExcel(any(MultipartFile.class));
    }

    @Test
    void testGetUserLoginData() throws Exception {
        String emailId = "user1@example.com";
        UserLoginData loginData = UserLoginData.builder()
                .emailId(emailId)
                .role(UserRole.ADMIN)
                .build();

        when(userService.getUserRoleAndBatch(emailId)).thenReturn(loginData);

        mockMvc.perform(get("/users/data/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.emailId").value(emailId))
                .andExpect(jsonPath("$.role").value(UserRole.ADMIN.toString()));

        verify(userService, times(1)).getUserRoleAndBatch(emailId);
    }

    @Test
    void testGetUserProfile() throws Exception {
        String emailId = "user1@example.com";
        User userProfile = User.builder()
                .emailId(emailId)
                .name("User 1")
                .role(UserRole.ADMIN).build();

        when(userService.getUserProfile(emailId)).thenReturn(userProfile);

        mockMvc.perform(get("/users/profile/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.emailId").value(emailId));

        verify(userService, times(1)).getUserProfile(emailId);
    }

}