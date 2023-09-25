package com.trainease.controller;

import com.trainease.entity.Course;
import com.trainease.entity.User;
import com.trainease.entity.UserRole;
import com.trainease.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(
            @RequestParam(name = "role", required = false) UserRole role,
            @RequestParam(name = "batchId", required = false) String batchId
    ) {
        return this.userService.getAllUsers(role, batchId);
    }

    @GetMapping("/users/{emailId}")
    public User getUserByEmailId(@PathVariable String emailId) {
        return this.userService.getUserByEmailId(emailId);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return this.userService.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/users/{emailId}")
    public String deleteUserByEmailId(@PathVariable String emailId) {
        return this.userService.deleteUserByEmailId(emailId);
    }

    @PostMapping("/users/saveExcel")
    public List<User> saveUsersFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return userService.saveUsersFromExcel(file);
    }

}
