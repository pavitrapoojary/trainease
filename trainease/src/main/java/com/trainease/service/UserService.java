package com.trainease.service;

import com.trainease.entity.User;
import com.trainease.entity.UserRole;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers(UserRole role, String batchId);

    User getUserByEmailId(String emailId);

    User createUser(User user);

    User updateUser(User user);

    String deleteUserByEmailId(String emailId);
    List<User> saveUsersFromExcel(MultipartFile excelFile) throws IOException;

}
