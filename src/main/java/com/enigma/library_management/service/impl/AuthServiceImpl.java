package com.enigma.library_management.service.impl;

import com.enigma.library_management.constant.Constant;
import com.enigma.library_management.dto.request.AuthRequest;
import com.enigma.library_management.dto.response.LoginResponse;
import com.enigma.library_management.dto.response.RegisterResponse;
import com.enigma.library_management.entity.User;
import com.enigma.library_management.service.AuthService;
import com.enigma.library_management.service.JwtService;
import com.enigma.library_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public LoginResponse login(AuthRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        User user = (User) authentication.getPrincipal();

        if (!authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constant.ERROR_INVALID_CREDENTIALS);
        }

        String generatedToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .username(user.getUsername())
                .token(generatedToken)
                .build();
    }

    @Override
    public RegisterResponse register(AuthRequest request) {
        validateUsernameNotExist(request.getUsername());

        User user = toUser(request.getUsername(), request.getPassword());

        userService.createUser(user);

        return toRegisterResponse(user);
    }

    private void validateUsernameNotExist(String username) {
        if (userService.getUserByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, Constant.ERROR_USERNAME_EXIST);
        }
    }

    private User toUser(String username, String password) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
    }

    private RegisterResponse toRegisterResponse(User user) {
        return RegisterResponse.builder()
                .username(user.getUsername())
                .build();
    }
}
