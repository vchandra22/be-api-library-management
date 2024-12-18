package com.enigma.library_management.controller;

import com.enigma.library_management.constant.Constant;
import com.enigma.library_management.dto.request.AuthRequest;
import com.enigma.library_management.dto.response.LoginResponse;
import com.enigma.library_management.dto.response.RegisterResponse;
import com.enigma.library_management.service.AuthService;
import com.enigma.library_management.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        RegisterResponse response = authService.register(request);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_REGISTER, response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        LoginResponse response = authService.login(request);

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_LOGIN, response);
    }
}
