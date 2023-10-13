package com.trainease.config;

import com.trainease.entity.User;
import com.trainease.dto.UserRole;
import com.trainease.repository.UserRepository;
import com.trainease.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DefaultUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findById("admin@ideas.com").isEmpty()) {
            User adminUser = User.builder()
                    .emailId("admin@ideas.com")
                    .name("Admin Main")
                    .role(UserRole.ADMIN)
                    .build();
            userService.createUser(adminUser);

        }
    }
}
