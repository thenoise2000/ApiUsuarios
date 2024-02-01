package com.models;

import com.entities.UserEntity;

import lombok.Data;

@Data
public class Phone {

    private Long id;

    private String number;

    private String cityCode;

    private String contryCode;

    private UserEntity userEntity;

}
