package com.enigma.library_management.service;

import com.enigma.library_management.dto.request.AuthRequest;
import com.enigma.library_management.dto.response.LoginResponse;
import com.enigma.library_management.dto.response.RegisterResponse;

public interface AuthService {
    LoginResponse login(AuthRequest request);
    RegisterResponse register(AuthRequest request);
}
