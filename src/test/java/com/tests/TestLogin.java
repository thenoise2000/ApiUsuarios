package com.tests;

import com.controller.LoginController;
import com.dto.ApiRequestDto;
import com.dto.response.ApiRespErrorDto;
import com.dto.response.RespJWT;
import com.models.User;
import com.security.ValidJwt;
import com.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(TestLogin.class)
class TestLogin {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    ValidJwt validJwt;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    private ApiRequestDto apiRequestDto;

    @BeforeEach
    void setUp() {
        
    	apiRequestDto = new ApiRequestDto("juan@rodriguez.org", "$Administrador=21");
        
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Juan");
        user.setEmail("juan@rodriguez.org");
        user.setPassword("$Administrador=21");
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJpYXQiOjE2OTU2NTk5NzgsImV4cCI6MTY5NTc0NjM3OH0.XpUlQp3LIU4vBQ6v2KJnf7LKAXFxMUftshAgMCsZT15JOANZ1G-eHlp9xihvePUlJi71P1mYBxRuPMLoze__Hw");
        user.setIsActive(true);
    }

    @Test
    void testAuthenticateUserSuccess() {

        String jwt = "jwt_token";
        LocalDateTime lastLogin = LocalDateTime.now();
        
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(apiRequestDto.getEmail(),
        		apiRequestDto.getPassword())))
                .thenReturn(mock(Authentication.class));
        
        when(validJwt.generateJwtToken(any(Authentication.class))).thenReturn(jwt);
        
        when(userService.findByEmail(apiRequestDto.getEmail())).thenReturn(Optional.of(user));
        when(userService.validateLoginByEmail(lastLogin,apiRequestDto.getEmail())).thenReturn(any(Integer.class));
        
        ResponseEntity<?> response = loginController.authenticateUser(apiRequestDto);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        RespJWT jwtResponse = (RespJWT) response.getBody();
        assertEquals(apiRequestDto.getEmail(), jwtResponse.getEmail());
        assertNotNull(jwtResponse.getLastLogin());
        assertNotNull(jwtResponse.getToken());
    }
    

}