package com.trainease.service;

import com.trainease.dto.AuthenticationRequest;
import com.trainease.repository.UserRepository;
import com.trainease.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.trainease.entity.User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username : " + username);
        }
        return new User(user.get().getEmailId(), user.get().getPassword(), new ArrayList<>());
    }

    public String authenticate(AuthenticationRequest authenticationRequest) throws Exception {
        final UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
        if (passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
            return jwtUtil.generateToken(userDetails);
        }
        throw new Exception("Incorrect password.");
    }

}
