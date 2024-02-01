package com.services;

import java.time.LocalDateTime;
import java.util.Optional;

import com.models.User;

public interface UserInt {

	User addUsers(User user);

    boolean validateEmail(String email);

    int validByLoginEmail(LocalDateTime lastLogin, String email);

    Optional<User> findByEmail(String email);
}
