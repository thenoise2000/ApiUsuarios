package com.tests;

import com.controller.ManageController;
import com.dto.UserDto;
import com.dto.response.UserDtoResp;
import com.mapp.MapDtoUser;
import com.models.User;
import com.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(TestLogin.class)
class TestUsers {
    @Mock
    private UserService userService;
    @Mock
    private MapDtoUser mapDtoUser;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private ManageController manageController;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testAddUser_Success() {
        
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName("Administrador");
        userDto.setEmail("administrador@admin.com");
        userDto.setPassword("password");

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        UserDtoResp userResponse = new UserDtoResp(user.getId(), user.getCreated(),
                user.getModified(), user.getLastLogin(), user.getToken(), user.getIsActive());

        when(userService.validateExistEmail(userDto.getEmail())).thenReturn(false);
        when(mapDtoUser.toDomain(userDto)).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userService.register(any(User.class))).thenReturn(user);
        
        ResponseEntity<Object> responseEntity = manageController.register(userDto);
        
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testAddUser_Conflict() {
        
        UserDto userDto = new UserDto();
        userDto.setName("Juan");
        userDto.setEmail("juan@rodriguez.org");
        userDto.setPassword("$Admin=25");

        when(userService.validateExistEmail(userDto.getEmail())).thenReturn(true);
        
        ResponseEntity<Object> responseEntity = manageController.register(userDto);
        
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

}