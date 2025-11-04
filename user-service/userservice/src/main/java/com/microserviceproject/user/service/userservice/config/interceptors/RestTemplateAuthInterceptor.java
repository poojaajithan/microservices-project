package com.microserviceproject.user.service.userservice.config.interceptors;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class RestTemplateAuthInterceptor implements ClientHttpRequestInterceptor{

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException 
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest servletRequest = attributes.getRequest();
            String authorizationHeader = servletRequest.getHeader("Authorization");
            if (authorizationHeader != null) {
                request.getHeaders().add("Authorization", authorizationHeader);
            }
        }
        return execution.execute(request, body);
    }

}
