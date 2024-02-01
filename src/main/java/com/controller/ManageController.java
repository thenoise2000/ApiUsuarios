package com.controller;

import com.dto.UserDto;
import com.dto.response.ApiRespErrorDto;
import com.dto.response.RespMsgErrorDto;
import com.dto.response.UserDtoResp;
import com.mapp.MapDtoUser;
import com.models.User;
import com.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/manage")
public class ManageController {

	@Autowired
    private UserService userService; 
    
	@Autowired
    private MapDtoUser mapDtoUser;  
    
	@Autowired
    private PasswordEncoder passEncoder;  
     
        
    @PostMapping("/users")  
    @ApiOperation("Agregar usuarios")  
    @ApiResponses({  
            @ApiResponse(code = 200, message = "OK"),  
            @ApiResponse(code = 500, message = "Error") }  
    )  
    public ResponseEntity<Object> register(@Valid @RequestBody UserDto userDto) {  
        if (userService.validateExistEmail(userDto.getEmail())) {  
            return ResponseEntity.status(HttpStatus.CONFLICT)  
                    .body(new RespMsgErrorDto("El correo ya está registrado"));  
        }  
         
        try {  
            userDto.setId(UUID.randomUUID());  
            userDto.setPassword(passEncoder.encode(userDto.getPassword()));  
            User user = userService.register(mapDtoUser.toDomain(userDto));  
            UserDtoResp resUsers = new UserDtoResp(  
                    user.getId(),  
                    user.getCreated(),  
                    user.getModified(),  
                    user.getLastLogin(),  
                    user.getToken(),  
                    user.getIsActive()  
            );  
            return ResponseEntity.status(HttpStatus.CREATED).body(resUsers);  
        } catch (Exception e) {  
            return new ResponseEntity<>(new ApiRespErrorDto(  
                    HttpStatus.INTERNAL_SERVER_ERROR,  
                    e.getMessage(),  
                    e.getLocalizedMessage()  
            ), HttpStatus.INTERNAL_SERVER_ERROR);  
        }  
    }  

}
