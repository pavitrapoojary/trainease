package com.trainease.controller;

import com.trainease.entity.User;
import com.trainease.dto.UserLoginData;
import com.trainease.dto.UserRole;
import com.trainease.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String homePage(){
        return "Hey test!";
    }

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

    @GetMapping("/users/data/{emailId}")
    public UserLoginData getUserLoginData(@PathVariable String emailId){
        return userService.getUserRoleAndBatch(emailId);
    }

    @GetMapping("/users/profile/{emailId}")
    public User getUserProfile(@PathVariable String emailId){
        return userService.getUserProfile(emailId);
    }

}
