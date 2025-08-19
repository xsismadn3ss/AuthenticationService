package com.grupo3.authentication_service.token.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface ITokenService {
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> claims, String username);
    String extractUsername(String token);
    void validateToken(String token);
    Boolean isExpired(String token);
}
