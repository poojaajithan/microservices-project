package com.microserviceproject.user.service.userservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserConfiguration {

    @Bean
    @LoadBalanced // Enable client-side load balancing, no hardcoded URLs, contact service discovery to get actual instance of the service
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
