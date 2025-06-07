package org.example.bazalotow2.service;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.flight.FlightCreateDTO;
import org.example.bazalotow2.dto.flight.FlightDTO;
import org.example.bazalotow2.entity.Airplane;
import org.example.bazalotow2.entity.City;
import org.example.bazalotow2.entity.Flight;
import org.example.bazalotow2.exception.notfound.EntityNotFoundException;
import org.example.bazalotow2.hateoas.FlightModelAssembler;
import org.example.bazalotow2.mapper.FlightMapper;
import org.example.bazalotow2.repository.AirplaneRepository;
import org.example.bazalotow2.repository.CityRepository;
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
    private final CityRepository cityRepository;
    private final FlightModelAssembler flightModelAssembler;
    private final FlightMapper flightMapper;
    public static final String simpleClassName = Flight.class.getSimpleName();

    public CollectionModel<EntityModel<FlightDTO>> getFlightsByAirplaneId(Long airplaneId) {
        Airplane foundAirplane = airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new EntityNotFoundException(airplaneId, AirplaneService.simpleClassName));

        List<Flight> flights = foundAirplane.getFlights();
        return flightModelAssembler.toCollectionModel(flights);
    }

    public CollectionModel<EntityModel<FlightDTO>> getFlightsDepartingFrom(Long cityId) {
        City foundCity = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException(cityId, CityService.simpleClassName));

        List<Flight> flights = foundCity.getDepartingFlights();
        return flightModelAssembler.toCollectionModel(flights);
    }

    public CollectionModel<EntityModel<FlightDTO>> getFlightsArrivingTo(Long cityId) {
        City foundCity = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException(cityId, CityService.simpleClassName));

        List<Flight> flights = foundCity.getArrivingFlights();
        return flightModelAssembler.toCollectionModel(flights);
    }

    public EntityModel<FlightDTO> getFlightById(Long flightId) {
        Flight foundFlight = flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException(flightId, simpleClassName));

        return flightModelAssembler.toModel(foundFlight);
    }

    public CollectionModel<EntityModel<FlightDTO>> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flightModelAssembler.toCollectionModel(flights);
    }

    public EntityModel<FlightDTO> createNewFlight(FlightCreateDTO flightCreateDTO) {
        Flight newFlight = flightMapper.toEntity(flightCreateDTO);

        City departureCity = cityRepository.findById(flightCreateDTO.departureCityId())
                .orElseThrow(() -> new EntityNotFoundException(flightCreateDTO.departureCityId(), CityService.simpleClassName));
        newFlight.setDepartureCity(departureCity);

        City arrivalCity = cityRepository.findById(flightCreateDTO.arrivalCityId())
                .orElseThrow(() -> new EntityNotFoundException(flightCreateDTO.arrivalCityId(), CityService.simpleClassName));
        newFlight.setArrivalCity(arrivalCity);

        Airplane airplane = airplaneRepository.findById(flightCreateDTO.airplaneId())
                .orElseThrow(() -> new EntityNotFoundException(flightCreateDTO.airplaneId(), AirplaneService.simpleClassName));
        newFlight.setAirplane(airplane);

        return flightModelAssembler.toModel(flightRepository.save(newFlight));
    }

    public void deleteFlight(Long flightId) {
        Flight foundFlight = flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException(flightId, simpleClassName));
        flightRepository.delete(foundFlight);
    }

    public EntityModel<FlightDTO> updateFlight(Long flightId, FlightCreateDTO flightCreateDTO) {
        Flight foundFlight = flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException(flightId, simpleClassName));

        foundFlight.setDepartureDateTime(flightCreateDTO.departureDateTime());
        foundFlight.setAvailableSeats(flightCreateDTO.availableSeats());

        City departureCity = cityRepository.findById(flightCreateDTO.departureCityId())
                .orElseThrow(() -> new EntityNotFoundException(flightCreateDTO.departureCityId(), CityService.simpleClassName));
        foundFlight.setDepartureCity(departureCity);

        City arrivalCity = cityRepository.findById(flightCreateDTO.arrivalCityId())
                .orElseThrow(() -> new EntityNotFoundException(flightCreateDTO.arrivalCityId(), CityService.simpleClassName));
        foundFlight.setArrivalCity(arrivalCity);

        Airplane airplane = airplaneRepository.findById(flightCreateDTO.airplaneId())
                .orElseThrow(() -> new EntityNotFoundException(flightCreateDTO.airplaneId(), AirplaneService.simpleClassName));
        foundFlight.setAirplane(airplane);

        return flightModelAssembler.toModel(flightRepository.save(foundFlight));
    }
}
