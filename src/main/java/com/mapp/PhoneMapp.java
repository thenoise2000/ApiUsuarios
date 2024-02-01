package com.mapp;

import com.entities.PhoneEntities;
import com.models.Phone;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserMapp.class})
public interface PhoneMapp {
    PhoneEntities toDbo(Phone phone);
    
    @InheritInverseConfiguration
    Phone toDomain(PhoneEntities phoneEntity);
}
