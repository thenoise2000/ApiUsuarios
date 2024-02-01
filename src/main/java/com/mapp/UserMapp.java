package com.mapp;

import com.entities.UserEntity;
import com.models.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface UserMapp {
    User toDomain(UserEntity usersEntities);
    
    @InheritInverseConfiguration
    UserEntity toDbo(User user);
}