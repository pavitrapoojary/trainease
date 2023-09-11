package com.trainease.controller;

import com.trainease.entity.User;
import com.trainease.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "batchId", required = false) String batchId
    ) {
        return this.userService.getAllUsers(role, batchId);
    }

    @GetMapping("/user/{emailId}")
    public User getUserByEmailId(@PathVariable String emailId) {
        return this.userService.getUserByEmailId(emailId);
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return this.userService.createUser(user);
    }

    @PutMapping("/user")
    public String updateUser(@RequestBody User user) {
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/user/{emailId}")
    public String deleteUserByEmailId(@PathVariable String emailId) {
        return this.userService.deleteUserByEmailId(emailId);
    }

}
