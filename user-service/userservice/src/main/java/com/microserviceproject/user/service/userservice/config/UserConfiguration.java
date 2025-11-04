package com.microserviceproject.user.service.userservice.config;

import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.microserviceproject.user.service.userservice.config.interceptors.RestTemplateAuthInterceptor;

@Configuration
public class UserConfiguration {

    @Bean
    @LoadBalanced // Enable client-side load balancing, no hardcoded URLs, contact service discovery to get actual instance of the service
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(List.of(new RestTemplateAuthInterceptor()));
        return restTemplate;
    }

}
