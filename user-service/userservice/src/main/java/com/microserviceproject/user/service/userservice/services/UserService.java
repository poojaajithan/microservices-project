package com.microserviceproject.user.service.userservice.services;

import java.util.List;

import com.microserviceproject.user.service.userservice.entities.User;

public interface UserService {
    //user operations
    //create user
    User saveUser(User user);

    //get all users
    List<User> getAllUsers();

    //get single user of given id
    User getUserById(String userId);

    //Update user
    User updateUser(User user, String userId);

    //delete user
    void deleteUser(String userId);
}
