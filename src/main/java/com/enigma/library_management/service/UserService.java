package com.enigma.library_management.service;

import com.enigma.library_management.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User getUserById(String id);
    User getUserByUsername(String username);
    User createUser(User user);
    User getByContext();
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
