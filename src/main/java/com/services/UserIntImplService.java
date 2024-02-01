package com.services;

import com.entities.UserEntity;
import com.mapp.UserMapp;
import com.models.User;
import com.repository.UserRepository;
import com.services.UserInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserIntImplService implements UserInt {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapp userMapperDbo;

    @Override
    public User addUsers(User user) {

        UserEntity userEntity = userMapperDbo.toDbo(user);

        if (userEntity.getPhones() != null) {
            userEntity.getPhones().forEach(phoneEntity -> {
                phoneEntity.setUserEntity(userEntity);
            });
        }
        return userMapperDbo.toDomain(userRepository.save(userEntity));
    }

    @Override
    public boolean validateEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public int validByLoginEmail(LocalDateTime lastLogin, String email) {
        return userRepository.validateLoginByEmail(lastLogin, email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userMapperDbo.toDomain(userRepository.findByEmail(email).orElse(null)));
    }

}
