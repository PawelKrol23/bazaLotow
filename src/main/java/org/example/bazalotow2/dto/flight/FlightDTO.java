package org.example.bazalotow2.dto.flight;

import org.example.bazalotow2.dto.airplane.AirplaneDTO;
import org.example.bazalotow2.dto.city.CityDTO;

import java.time.LocalDateTime;

public record FlightDTO(
    Long id,
    CityDTO departureCity,
    CityDTO arrivalCity,
    LocalDateTime departureDateTime,
    int availableSeats,
    AirplaneDTO airplane
) {
}
