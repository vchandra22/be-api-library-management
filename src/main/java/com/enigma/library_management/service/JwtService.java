package com.enigma.library_management.service;

import com.enigma.library_management.entity.User;

public interface JwtService {
    String generateToken(User user);
    boolean verifyToken(String bearerToken);
    String getUserIdByToken(String bearerAuthTokenFromHeader);
}
