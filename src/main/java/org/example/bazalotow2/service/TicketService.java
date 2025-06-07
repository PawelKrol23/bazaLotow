package org.example.bazalotow2.service;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.ticket.TicketDTO;
import org.example.bazalotow2.entity.Ticket;
import org.example.bazalotow2.exception.notfound.EntityNotFoundException;
import org.example.bazalotow2.hateoas.TicketModelAssembler;
import org.example.bazalotow2.repository.TicketRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketModelAssembler ticketModelAssembler;
    public static final String simpleClassName = Ticket.class.getSimpleName();

    public EntityModel<TicketDTO> getTicketById(Long ticketId) {
        Ticket foundTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException(ticketId, simpleClassName));

        return ticketModelAssembler.toModel(foundTicket);
    }
}
