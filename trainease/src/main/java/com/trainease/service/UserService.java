package com.trainease.service;

import com.trainease.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(String role, String batchId);

    User getUserByEmailId(String emailId);

    User createUser(User user);

    String updateUser(User user);

    String deleteUserByEmailId(String emailId);
}
