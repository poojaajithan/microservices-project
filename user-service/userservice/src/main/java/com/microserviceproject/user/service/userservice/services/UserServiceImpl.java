package com.microserviceproject.user.service.userservice.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microserviceproject.user.service.userservice.entities.Hotel;
import com.microserviceproject.user.service.userservice.entities.Rating;
import com.microserviceproject.user.service.userservice.entities.User;
import com.microserviceproject.user.service.userservice.exceptions.ResourceNotFoundException;
import com.microserviceproject.user.service.userservice.external.services.HotelService;
import com.microserviceproject.user.service.userservice.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        //fetch rating of user from rating service
        Rating[] ratingsArray = restTemplate.getForObject(
                "http://RATINGSERVICE/ratings/user/" + userId,
                Rating[].class
        );
        
        List<Rating> ratingsList = new ArrayList<>();
        
        log.info("Ratings received from Rating Service for userId {}", userId);
        
        if (ratingsArray != null) {
            ratingsList = Arrays.stream(ratingsArray)
                .map(rating -> {
                    try {
                        //fetch hotel info for each rating using restTemplate
                        /*Hotel hotel = restTemplate.getForObject(
                                "http://HOTELSERVICE/hotels/" + rating.getHotelId(),
                                Hotel.class
                        );*/
                        //fetch hotel info for each rating using Feign client
                        Hotel hotel = hotelService.getHotel(rating.getHotelId());
                        
                        log.info("Hotel info received for hotelId {}: {}", rating.getHotelId(), hotel);
                        rating.setHotel(hotel);
                    } catch (Exception e) {
                        log.error("Error getting hotel info for hotelId {}: {}", rating.getHotelId(), e.getMessage());
                        // Still set null hotel on error
                        rating.setHotel(null);
                    }
                    return rating;
                })
                .collect(Collectors.toList());
        }

        user.setRatings(ratingsList);

        return user;
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
