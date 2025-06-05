package org.example.bazalotow2.mapper;

import org.example.bazalotow2.dto.ticket.TicketDTO;
import org.example.bazalotow2.entity.Ticket;
import org.mapstruct.Mapper;

@Mapper
public interface TicketMapper {
    TicketDTO toDto(Ticket ticket);
}
