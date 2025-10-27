package com.microserviceproject.user.service.userservice.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.microserviceproject.user.service.userservice.entities.Rating;


@Service
@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

    @PostMapping("/ratings/")
    public Rating createRating(Rating rating);

    @PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable("ratingId") String ratingId, Rating rating);

    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable("ratingId") String ratingId);    
}
