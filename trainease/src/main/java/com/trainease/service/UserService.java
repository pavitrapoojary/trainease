package com.trainease.service;

import com.trainease.entity.User;
import com.trainease.entity.UserRole;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(UserRole role, String batchId);

    User getUserByEmailId(String emailId);

    User createUser(User user);

    String updateUser(User user);

    String deleteUserByEmailId(String emailId);
}
