package com.trainease.controller;


import com.trainease.dto.AuthenticationRequest;
import com.trainease.dto.AuthenticationResponse;
import com.trainease.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserAuthService userAuthService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(new AuthenticationResponse(userAuthService.authenticate(authenticationRequest)));
    }
}
