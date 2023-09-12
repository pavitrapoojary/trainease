package com.trainease.controller;

import com.trainease.entity.User;
import com.trainease.entity.UserRole;
import com.trainease.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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
    void getAllUsersNoParam() throws Exception {
        User user1 = User.builder().emailId("test1@gmail.com").name("test1").role(UserRole.ADMIN).build();
        User user2 = User.builder().emailId("test2@gmail.com").name("test2").role(UserRole.TRAINEE).batchId("B1").build();
        List<User> userList = Arrays.asList(user1, user2);
        when(userService.getAllUsers(null, null)).thenReturn(userList);
        MvcResult responseResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = responseResult.getResponse().getContentAsString();
        assertEquals(200, responseResult.getResponse().getStatus());
        assertThat(responseBody).contains(user1.getEmailId(), user2.getEmailId());
        verify(userService).getAllUsers(null, null);
    }

}