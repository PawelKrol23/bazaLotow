package org.example.bazalotow2.controller;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.service.FlightService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
}
