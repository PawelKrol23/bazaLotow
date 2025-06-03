package org.example.bazalotow2.hateoas;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.controller.AirplaneController;
import org.example.bazalotow2.dto.airplane.AirplaneDTO;
import org.example.bazalotow2.entity.Airplane;
import org.example.bazalotow2.mapper.AirplaneMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class AirplaneModelAssembler implements RepresentationModelAssembler<Airplane, EntityModel<AirplaneDTO>> {
    private final AirplaneMapper airplaneMapper;

    @Override
    public EntityModel<AirplaneDTO> toModel(Airplane airplane) {
        return EntityModel.of(airplaneMapper.toDto(airplane),
            linkTo(methodOn(AirplaneController.class).getAirplaneById(airplane.getId())).withSelfRel(),
            linkTo(methodOn(AirplaneController.class).deleteAirplane(airplane.getId())).withRel("delete"),
            linkTo(methodOn(AirplaneController.class).updateAirplane(airplane.getId(), null)).withRel("update")
        );
    }

    public CollectionModel<EntityModel<AirplaneDTO>> toCollectionModel(List<Airplane> airplanes) {
        List<EntityModel<AirplaneDTO>> entityModels = airplanes.stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(entityModels,
            linkTo(methodOn(AirplaneController.class).getAllAirplanes()).withSelfRel()
        );
    }
}
