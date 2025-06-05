package org.example.bazalotow2.dto.ticket;

import org.example.bazalotow2.dto.flight.FlightDTO;
import org.example.bazalotow2.dto.user.UserDTO;

import java.time.LocalDateTime;

public record TicketDTO(
    Long id,
    FlightDTO flight,
    UserDTO user,
    LocalDateTime purchaseDate,
    boolean confirmed
) {
}
