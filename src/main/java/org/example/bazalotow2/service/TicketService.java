package org.example.bazalotow2.service;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
}
