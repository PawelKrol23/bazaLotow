package org.example.bazalotow2.service;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.flight.FlightDTO;
import org.example.bazalotow2.entity.Airplane;
import org.example.bazalotow2.entity.Flight;
import org.example.bazalotow2.exception.notfound.EntityNotFoundException;
import org.example.bazalotow2.hateoas.AirplaneModelAssembler;
import org.example.bazalotow2.hateoas.FlightModelAssembler;
import org.example.bazalotow2.mapper.AirplaneMapper;
import org.example.bazalotow2.repository.AirplaneRepository;
import org.example.bazalotow2.repository.FlightRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirplaneRepository airplaneRepository;
    private final FlightModelAssembler flightModelAssembler;
    private final AirplaneMapper airplaneMapper;
    public static final String simpleClassName = Flight.class.getSimpleName();

    public CollectionModel<EntityModel<FlightDTO>> getFlightsByAirplaneId(Long airplaneId) {
        Airplane foundAirplane = airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new EntityNotFoundException(airplaneId, AirplaneService.simpleClassName));

        List<Flight> flights = foundAirplane.getFlights();
        return flightModelAssembler.toCollectionModel(flights);
    }
}
