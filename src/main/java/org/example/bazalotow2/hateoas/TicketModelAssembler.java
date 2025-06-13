package org.example.bazalotow2.hateoas;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.controller.TicketController;
import org.example.bazalotow2.dto.ticket.TicketDTO;
import org.example.bazalotow2.entity.Ticket;
import org.example.bazalotow2.mapper.TicketMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class TicketModelAssembler implements RepresentationModelAssembler<Ticket, EntityModel<TicketDTO>> {
    private final TicketMapper ticketMapper;

    @Override
    public EntityModel<TicketDTO> toModel(Ticket ticket) {
        return EntityModel.of(ticketMapper.toDto(ticket),
            linkTo(methodOn(TicketController.class).getTicketById(ticket.getId(), null)).withSelfRel(),
            linkTo(methodOn(TicketController.class).confirmTicket(ticket.getId(), null)).withRel("confirm"),
            linkTo(methodOn(TicketController.class).deleteTicket(ticket.getId(), null)).withRel("delete")
        );
    }

    public CollectionModel<EntityModel<TicketDTO>> toCollectionModel(List<Ticket> tickets) {
        List<EntityModel<TicketDTO>> entityModels = tickets.stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(entityModels,
            linkTo(methodOn(TicketController.class).getOwnTickets(null)).withSelfRel()
        );
    }
}
