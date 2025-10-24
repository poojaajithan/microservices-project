package com.microserviceproject.ratingservice.services;

import java.util.List;

import com.microserviceproject.ratingservice.entities.Rating;

public interface RatingService {
    //create
    Rating createRating(Rating rating);

    //get all ratings
    List<Rating> getAllRatings();

    //get all by userId
    List<Rating> getAllRatingsByUserId(String userId);

    //get all by hotelId
    List<Rating> getAllRatingsByHotelId(String hotelId);
}
