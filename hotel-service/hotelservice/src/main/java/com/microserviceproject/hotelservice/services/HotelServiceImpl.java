package com.microserviceproject.hotelservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.microserviceproject.hotelservice.entities.Hotel;
import com.microserviceproject.hotelservice.exceptions.ResourceNotFoundException;
import com.microserviceproject.hotelservice.repositories.HotelRepository;


@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel createHotel(Hotel hotel) {
        UUID uuid = UUID.randomUUID();
        hotel.setId(uuid.toString());
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(String hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

}
