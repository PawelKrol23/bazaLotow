package org.example.bazalotow2.hateoas;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.controller.CityController;
import org.example.bazalotow2.dto.city.CityDTO;
import org.example.bazalotow2.entity.City;
import org.example.bazalotow2.mapper.CityMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CityModelAssembler implements RepresentationModelAssembler<City, EntityModel<CityDTO>> {
    private final CityMapper cityMapper;

    @Override
    public EntityModel<CityDTO> toModel(City city) {
        return EntityModel.of(cityMapper.toDto(city),
                linkTo(methodOn(CityController.class).getCityById(city.getId())).withSelfRel()
        );
    }

    public CollectionModel<EntityModel<CityDTO>> toCollectionModel(List<City> cities) {
        List<EntityModel<CityDTO>> entityModels = cities.stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(entityModels,
            linkTo(methodOn(CityController.class).getAllCities()).withSelfRel()
        );
    }
}
