package org.example.bazalotow2.service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.time.format.DateTimeFormatter;
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

    @SneakyThrows
    public void writePdfToResponse(HttpServletResponse response, Long ticketId, UserPrincipal userPrincipal) {
        Ticket foundTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException(ticketId, simpleClassName));

        if (!isUserOwnerOrAdmin(foundTicket, userPrincipal)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");
        }

        if (!foundTicket.isConfirmed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ticket is not confirmed yet!");
        }

        response.setContentType("application/pdf");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=document.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Title
        Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLACK);
        Paragraph title = new Paragraph("Flight Ticket", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Ticket info
        Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.DARK_GRAY);
        document.add(new Paragraph("Passenger Name: %s %s".formatted(foundTicket.getUser().getFirstName(), foundTicket.getUser().getLastName()), normalFont));
        document.add(new Paragraph("Flight id: %d".formatted(foundTicket.getFlight().getId()), normalFont));
        document.add(new Paragraph("From: %s".formatted(foundTicket.getFlight().getDepartureCity().getName()), normalFont));
        document.add(new Paragraph("To: %s".formatted(foundTicket.getFlight().getArrivalCity().getName()), normalFont));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        document.add(new Paragraph("Date: %s".formatted(foundTicket.getFlight().getDepartureDateTime().format(dateFormatter)), normalFont));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        document.add(new Paragraph("Departure Time: %s".formatted(foundTicket.getFlight().getDepartureDateTime().format(timeFormatter)), normalFont));
        document.add(new Paragraph("Ticket id: %d".formatted(foundTicket.getId()), normalFont));

        document.close();
    }

    private boolean isUserOwnerOrAdmin(Ticket ticket, UserPrincipal userPrincipal) {
        return userPrincipal.getUsername().equals(ticket.getUser().getEmail())
                || userPrincipal.isAdmin();
    }
}
