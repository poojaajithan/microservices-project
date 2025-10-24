package com.microserviceproject.hotelservice.services;

import java.util.List;

import com.microserviceproject.hotelservice.entities.Hotel;

public interface HotelService {
    //create
    Hotel createHotel(Hotel hotel);

    //get single hotel
    Hotel getHotelById(String hotelId);

    List<Hotel> getAllHotels();

}
