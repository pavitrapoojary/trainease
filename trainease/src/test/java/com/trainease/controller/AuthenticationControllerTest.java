package com.trainease.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainease.dto.AuthenticationRequest;
import com.trainease.service.UserAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class AuthenticationControllerTest {
    @Mock
    UserAuthService userAuthService;

    @InjectMocks
    AuthenticationController authenticationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void testCreateAuthenticationToken() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username("test@example.com")
                .password("password")
                .build();
        String jwtToken = "jwtToken";

        when(userAuthService.authenticate(authenticationRequest)).thenReturn(jwtToken);

        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.jwt").value(jwtToken));

        verify(userAuthService, times(1)).authenticate(authenticationRequest);
    }


}