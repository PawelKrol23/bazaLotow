package org.example.bazalotow2.mapper;

import org.example.bazalotow2.dto.airplane.AirplaneCreateDTO;
import org.example.bazalotow2.dto.airplane.AirplaneDTO;
import org.example.bazalotow2.entity.Airplane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirplaneMapper {
    Airplane toEntity(AirplaneCreateDTO airplaneCreateDTO);
    AirplaneDTO toDto(Airplane airplane);
}
