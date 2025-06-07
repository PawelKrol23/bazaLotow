package org.example.bazalotow2.dto.flight;

import java.time.LocalDateTime;

public record FlightCreateDTO(
    Long departureCityId,
    Long arrivalCityId,
    LocalDateTime departureDateTime,
    int availableSeats,
    Long airplaneId
) {
}
