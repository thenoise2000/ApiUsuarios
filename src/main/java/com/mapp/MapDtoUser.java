package com.mapp;

import com.dto.UserDto;
import com.models.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {MapDtoPhone.class})
public interface MapDtoUser {
    MapDtoUser INSTANCE = Mappers.getMapper(MapDtoUser.class);

    User toDomain(UserDto userDto);

    @InheritInverseConfiguration
    UserDto toDto(User user);
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "password", target = "password"),
        @Mapping(source = "phones", target = "phones")
    })
    UserDto toDto(UserDto userDto);
}

