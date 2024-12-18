package com.enigma.library_management.service.impl;

import com.enigma.library_management.entity.User;
import com.enigma.library_management.repository.UserRepository;
import com.enigma.library_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println("loadUserByUsername: " + user);

        return (UserDetails) userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}
