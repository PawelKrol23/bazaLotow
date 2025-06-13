package org.example.bazalotow2.service;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.config.security.UserPrincipal;
import org.example.bazalotow2.dto.ticket.TicketCreateDTO;
import org.example.bazalotow2.dto.ticket.TicketDTO;
import org.example.bazalotow2.entity.Flight;
import org.example.bazalotow2.entity.Ticket;
import org.example.bazalotow2.entity.User;
import org.example.bazalotow2.exception.notfound.EntityNotFoundException;
import org.example.bazalotow2.hateoas.TicketModelAssembler;
import org.example.bazalotow2.mapper.TicketMapper;
import org.example.bazalotow2.repository.FlightRepository;
import org.example.bazalotow2.repository.TicketRepository;
import org.example.bazalotow2.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final TicketModelAssembler ticketModelAssembler;
    public static final String simpleClassName = Ticket.class.getSimpleName();
    private final TicketMapper ticketMapper;

    public EntityModel<TicketDTO> getTicketById(Long ticketId, UserPrincipal userPrincipal) {
        Ticket foundTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException(ticketId, simpleClassName));

        if (!isUserOwnerOrAdmin(foundTicket, userPrincipal)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");
        }

        return ticketModelAssembler.toModel(foundTicket);
    }

    public CollectionModel<EntityModel<TicketDTO>> getTicketsByFlightId(Long flightId) {
        Flight foundFlight = flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException(flightId, FlightService.simpleClassName));

        List<Ticket> tickets = foundFlight.getTickets();
        return ticketModelAssembler.toCollectionModel(tickets);
    }

    public CollectionModel<EntityModel<TicketDTO>> getUsersTickets(UserPrincipal userPrincipal) {
        List<Ticket> tickets;
        if (userPrincipal.isAdmin()) {
            tickets = ticketRepository.findAll();
        } else {
            User foundUser = userRepository.findByEmail(userPrincipal.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException(0, UserService.simpleClassName));
            tickets = foundUser.getTickets();
        }

        return ticketModelAssembler.toCollectionModel(tickets);
    }

    @Transactional
    public EntityModel<TicketDTO> createNewTicket(TicketCreateDTO ticketCreateDTO, UserPrincipal userPrincipal) {
        Ticket newTicket = ticketMapper.toEntity(ticketCreateDTO);

        Flight foundFlight = flightRepository.findById(ticketCreateDTO.flightId())
                .orElseThrow(() -> new EntityNotFoundException(ticketCreateDTO.flightId(), FlightService.simpleClassName));
        newTicket.setFlight(foundFlight);

        User foundUser = userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(0, UserService.simpleClassName));
        newTicket.setUser(foundUser);

        foundFlight.setAvailableSeats(foundFlight.getAvailableSeats() - 1);
        flightRepository.save(foundFlight);
        return ticketModelAssembler.toModel(ticketRepository.save(newTicket));
    }

    public void deleteTicket(Long ticketId, UserPrincipal userPrincipal) {
        Ticket foundTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException(ticketId, simpleClassName));

        if (!isUserOwnerOrAdmin(foundTicket, userPrincipal)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");
        }

        ticketRepository.delete(foundTicket);
    }

    public EntityModel<TicketDTO> confirmTicket(Long ticketId, UserPrincipal userPrincipal) {
        Ticket foundTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException(ticketId, simpleClassName));

        if (!isUserOwnerOrAdmin(foundTicket, userPrincipal)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");
        }

        foundTicket.setConfirmed(true);
        return ticketModelAssembler.toModel(foundTicket);
    }

    private boolean isUserOwnerOrAdmin(Ticket ticket, UserPrincipal userPrincipal) {
        return userPrincipal.getUsername().equals(ticket.getUser().getEmail())
                || userPrincipal.isAdmin();
    }
}
