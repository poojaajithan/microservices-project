package com.microserviceproject.apigateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microserviceproject.apigateway.model.AuthResponse;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(
                @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient authorizedClient,
                @AuthenticationPrincipal OidcUser user,
                Model model)
    {
        log.info("user email id : {} ", user.getEmail());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUserId(user.getEmail());
        authResponse.setAccessToken(authorizedClient.getAccessToken().getTokenValue());
        authResponse.setRefreshToken(authorizedClient.getRefreshToken().getTokenValue());
        authResponse.setExpiresAt(authorizedClient.getAccessToken().getExpiresAt().toString());

        List<String> authorities = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();

        authResponse.setAuthorities(authorities);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    
}
