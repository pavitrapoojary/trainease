package com.trainease.service;

import com.trainease.entity.User;
import com.trainease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers(String role, String batchId) {
        if (role!=null && role.equals("TRAINEE") && batchId != null) {
            return userRepository.findAll().stream()
                    .filter(
                            user -> user.getRole().equals(role) &&
                                    user.getBatchId().equals(batchId))
                    .collect(Collectors.toList());
        }
        if (role != null) {
            return userRepository.findAll().stream()
                    .filter(
                            user -> user.getRole().equals(role))
                    .collect(Collectors.toList());
        }

        if (batchId != null) {
            return userRepository.findAll().stream()
                    .filter(
                            user -> user.getBatchId()!=null && user.getBatchId().equals(batchId))
                    .collect(Collectors.toList());
        }

        return userRepository.findAll();
    }

    @Override
    public User getUserByEmailId(String emailId) {
        return userRepository.findById(emailId).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public String updateUser(User user) {
        //future scope
        Optional<User>existingUserOptional = userRepository.findById(user.getEmailId());
        if(existingUserOptional.isPresent()){
            User existingUser = existingUserOptional.get();
            existingUser.setName(user.getName());
            existingUser.setRole(user.getRole());
            existingUser.setBatchId(user.getBatchId());
            userRepository.save(existingUser);
            return "User "+user.getEmailId()+" updated successfully!";
        }
        return "User "+user.getEmailId()+" does not exist.";
    }

    @Override
    public String deleteUser(String emailId) {
        if(userRepository.findById(emailId).isPresent()){
            userRepository.deleteById(emailId);
            return "User "+emailId+" deleted successfully!";
        }
        return "User "+emailId+" does not exist.";
    }



}
