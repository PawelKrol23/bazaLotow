package org.example.bazalotow2.hateoas;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.controller.AirplaneController;
import org.example.bazalotow2.dto.airplane.AirplaneDTO;
import org.example.bazalotow2.dto.flight.FlightDTO;
import org.example.bazalotow2.entity.Airplane;
import org.example.bazalotow2.entity.Flight;
import org.example.bazalotow2.mapper.FlightMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class FlightModelAssembler implements RepresentationModelAssembler<Flight, EntityModel<FlightDTO>> {
    private final FlightMapper flightMapper;

    @Override
    public EntityModel<FlightDTO> toModel(Flight flight) {
        return EntityModel.of(flightMapper.toDto(flight),
            linkTo(methodOn(AirplaneController.class).getAirplaneById(flight.getAirplane().getId())).withRel("airplane")
        );
    }

    public CollectionModel<EntityModel<FlightDTO>> toCollectionModel(List<Flight> flights) {
        List<EntityModel<FlightDTO>> entityModels = flights.stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(entityModels

        );
    }
}
