package com.controller;

import com.dto.ApiRequestDto;
import com.dto.response.ApiRespErrorDto;
import com.dto.response.RespJWT;
import com.models.User;
import com.security.ValidJwt;
import com.services.UserDetailsImpl;
import com.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {
	
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    ValidJwt validJwt;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("Enter email and password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Error") }
    )
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody ApiRequestDto loginRequest) {

        try {
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = validJwt.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Clock clock = Clock.system(ZoneId.of("America/Caracas"));
            LocalDateTime today = LocalDateTime.now(clock);

            Optional<User> user = userService.findByEmail(loginRequest.getEmail());
            LocalDateTime localDateTime = Optional.ofNullable(user.get().getLastLogin()).orElse(today);

            userService.validateLoginByEmail(today, loginRequest.getEmail());

            return ResponseEntity.ok(new RespJWT(
                    loginRequest.getEmail(),
                    localDateTime,
                    jwt
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespErrorDto(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(), e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
