package com.mapp;

import com.dto.PhoneDto;
import com.models.Phone;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring") 
public interface MapDtoPhone { 
    Phone toDomain(PhoneDto phoneDto); 
 
    @InheritInverseConfiguration 
    PhoneDto toDto(Phone phone); 
     
    @AfterMapping 
    default void mapCodes(PhoneDto phoneDto, @MappingTarget Phone phone) { 
        phone.setNumber(phoneDto.getNumber()); 
        phone.setCityCode(phoneDto.getCityCode()); 
        phone.setContryCode(phoneDto.getContryCode()); 
    } 
}
