package com.microserviceproject.user.service.userservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.microserviceproject.user.service.userservice.entities.Rating;
import com.microserviceproject.user.service.userservice.external.services.RatingService;

@SpringBootTest
class UserserviceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
    private RatingService ratingService;

	@Test
	public void createRatingTest() {
		Rating rating = Rating.builder()
			.userId("test-user-123")
			.hotelId("test-hotel-456")
			.rating(4)
			.review("Great stay!")
			.build();

		Rating createdRating = ratingService.createRating(rating);
		assertNotNull(createdRating, "Created rating should not be null");
		assertNotNull(createdRating.getRatingId(), "Rating ID should not be null");
	}
}
