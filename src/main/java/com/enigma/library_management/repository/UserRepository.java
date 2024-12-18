package com.enigma.library_management.repository;

import com.enigma.library_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
}
