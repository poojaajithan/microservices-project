package com.microserviceproject.user.service.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rating {
    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String review;
    private Hotel hotel;
}
