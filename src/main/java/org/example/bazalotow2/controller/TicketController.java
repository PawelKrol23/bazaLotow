package org.example.bazalotow2.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.config.security.UserPrincipal;
import org.example.bazalotow2.dto.ticket.TicketCreateDTO;
import org.example.bazalotow2.dto.ticket.TicketDTO;
import org.example.bazalotow2.service.TicketService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{ticketId}")
    @SecurityRequirement(name = "basicAuth")
    public EntityModel<TicketDTO> getTicketById(@PathVariable Long ticketId,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ticketService.getTicketById(ticketId, userPrincipal);
    }

    @GetMapping
    @SecurityRequirement(name = "basicAuth")
    public CollectionModel<EntityModel<TicketDTO>> getOwnTickets(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ticketService.getUsersTickets(userPrincipal);
    }

    @PostMapping
    @SecurityRequirement(name = "basicAuth")
    public EntityModel<TicketDTO> createNewTicket(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                  @RequestBody TicketCreateDTO ticketCreateDTO) {
        return ticketService.createNewTicket(ticketCreateDTO, userPrincipal);
    }

    @DeleteMapping("/{ticketId}")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ticketService.deleteTicket(ticketId, userPrincipal);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{ticketId}")
    @SecurityRequirement(name = "basicAuth")
    public EntityModel<TicketDTO> confirmTicket(@PathVariable Long ticketId,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ticketService.confirmTicket(ticketId, userPrincipal);
    }

    @GetMapping("/{ticketId}/pdf")
    @SecurityRequirement(name = "basicAuth")
    public void generateTicketPdf(@PathVariable Long ticketId,
                                  @AuthenticationPrincipal UserPrincipal userPrincipal,
                                  HttpServletResponse response) {
        ticketService.writePdfToResponse(response, ticketId, userPrincipal);
    }
}
