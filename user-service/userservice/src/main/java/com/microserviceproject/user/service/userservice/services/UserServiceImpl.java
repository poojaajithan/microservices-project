package com.microserviceproject.user.service.userservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microserviceproject.user.service.userservice.entities.User;
import com.microserviceproject.user.service.userservice.exceptions.ResourceNotFoundException;
import com.microserviceproject.user.service.userservice.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public User saveUser(User user) {
        UUID uuid = UUID.randomUUID();
        user.setUserId(uuid.toString());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, String userId) {
        user.setUserId(userId);
        return userRepository.save(user);
    }

}
