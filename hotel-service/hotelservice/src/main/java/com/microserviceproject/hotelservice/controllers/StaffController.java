package com.microserviceproject.hotelservice.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/staffs")
public class StaffController {
    
    @GetMapping
    public ResponseEntity<List<String>> getAllStaffs() {
        List<String> staffs = List.of("Alice", "Bob", "Charlie");
        return new ResponseEntity<>(staffs, HttpStatus.OK);
    }
}
