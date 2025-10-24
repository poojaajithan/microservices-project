package com.microserviceproject.hotelservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microserviceproject.hotelservice.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {

}
