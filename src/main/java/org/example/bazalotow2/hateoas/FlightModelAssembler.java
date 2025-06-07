package org.example.bazalotow2.hateoas;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.controller.FlightController;
import org.example.bazalotow2.dto.flight.FlightDTO;
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
                linkTo(methodOn(FlightController.class).getFlightById(flight.getId())).withSelfRel(),
                linkTo(methodOn(FlightController.class).updateFlight(flight.getId(), null)).withRel("update"),
                linkTo(methodOn(FlightController.class).deleteFlight(flight.getId())).withRel("delete")
        );
    }

    public CollectionModel<EntityModel<FlightDTO>> toCollectionModel(List<Flight> flights) {
        List<EntityModel<FlightDTO>> entityModels = flights.stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(entityModels,
            linkTo(methodOn(FlightController.class).getAllFlights()).withSelfRel()
        );
    }
}
