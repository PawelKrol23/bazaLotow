package org.example.bazalotow2.controller;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.service.TicketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
}
