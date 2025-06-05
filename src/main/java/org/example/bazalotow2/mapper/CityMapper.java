package org.example.bazalotow2.mapper;

import org.example.bazalotow2.dto.city.CityDTO;
import org.example.bazalotow2.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDTO toDto(City city);
}
