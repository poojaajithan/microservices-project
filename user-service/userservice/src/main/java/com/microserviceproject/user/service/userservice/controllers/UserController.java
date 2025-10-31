package com.microserviceproject.user.service.userservice.controllers;

import java.util.List;

import org.hibernate.engine.jdbc.env.internal.LobCreationLogging_.logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microserviceproject.user.service.userservice.entities.User;
import com.microserviceproject.user.service.userservice.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userResponse = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    //single user get
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    //all users get
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }  

    //maintain same return type as original method
    public ResponseEntity<User> getUserByIdFallback(String userId, Exception ex) {
        log.error("Fallback executed for getUserById with userId: {} due to exception: {}", userId, ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
    }
}
    
