package com.services;

import com.models.User;
import com.security.ValidJwt;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService{
	
	 @Autowired
	    AuthenticationManager authenticationManager;

	    @Autowired
	    ValidJwt jwtUtils;
	    private final UserInt userPortRepository;
	    public User register(User user){

	        Clock clock = Clock.system(ZoneId.of("America/Caracas"));
	        LocalDateTime today = LocalDateTime.now(clock);
	        user.setCreated(today);
	        user.setModified(today);
	        user.setLastLogin(today);
	        user.setIsActive(true);
	        user.setToken(jwtUtils.generateJwtTokenWithoutAuth(user.getEmail()));

	        return userPortRepository.addUsers(user);
	    }

	    public boolean validateExistEmail(String email){
	        return userPortRepository.validateEmail(email);
	    }

	    public int validateLoginByEmail(LocalDateTime lastLogin, String email){
	        return userPortRepository.validByLoginEmail(lastLogin, email);
	    }

	    public Optional<User> findByEmail(String email) {
	        return userPortRepository.findByEmail(email);
	    }
}
