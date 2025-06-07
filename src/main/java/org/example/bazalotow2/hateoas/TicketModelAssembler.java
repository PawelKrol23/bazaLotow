package org.example.bazalotow2.hateoas;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.ticket.TicketDTO;
import org.example.bazalotow2.entity.Ticket;
import org.example.bazalotow2.mapper.TicketMapper;
import org.example.bazalotow2.service.TicketService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class TicketModelAssembler implements RepresentationModelAssembler<Ticket, EntityModel<TicketDTO>> {
    private final TicketMapper ticketMapper;


    @Override
    public EntityModel<TicketDTO> toModel(Ticket ticket) {
        return EntityModel.of(ticketMapper.toDto(ticket),
                linkTo(methodOn(TicketService.class).getTicketById(ticket.getId())).withSelfRel()
        );
    }
}
