package org.example.bazalotow2.controller;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.ticket.TicketDTO;
import org.example.bazalotow2.service.TicketService;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{ticketId}")
    public EntityModel<TicketDTO> getTicketById(@PathVariable Long ticketId) {
        return ticketService.getTicketById(ticketId);
    }
}
