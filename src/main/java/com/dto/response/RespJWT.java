package com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RespJWT {

    private String email;

    private LocalDateTime lastLogin;

    private String token;

}

