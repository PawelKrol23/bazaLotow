package org.example.bazalotow2.mapper;

import org.example.bazalotow2.dto.airplane.AirplaneCreateDTO;
import org.example.bazalotow2.dto.flight.FlightDTO;
import org.example.bazalotow2.entity.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    Flight toEntity(AirplaneCreateDTO airplaneCreateDTO);
    FlightDTO toDto(Flight flight);
}
