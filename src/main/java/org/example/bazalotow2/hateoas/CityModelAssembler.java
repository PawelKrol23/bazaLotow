package org.example.bazalotow2.hateoas;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.city.CityDTO;
import org.example.bazalotow2.entity.City;
import org.example.bazalotow2.mapper.CityMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityModelAssembler implements RepresentationModelAssembler<City, EntityModel<CityDTO>> {
    private final CityMapper cityMapper;


    @Override
    public EntityModel<CityDTO> toModel(City city) {
        return EntityModel.of(cityMapper.toDto(city)
        );
    }
}
