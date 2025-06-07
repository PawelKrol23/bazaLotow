package org.example.bazalotow2.controller;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.flight.FlightCreateDTO;
import org.example.bazalotow2.dto.flight.FlightDTO;
import org.example.bazalotow2.service.FlightService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping("/{flightId}")
    public EntityModel<FlightDTO> getFlightById(@PathVariable Long flightId) {
        return flightService.getFlightById(flightId);
    }

    @GetMapping
    public CollectionModel<EntityModel<FlightDTO>> getAllFlights() {
        return flightService.getAllFlights();
    }

    @PostMapping
    public EntityModel<FlightDTO> createNewFlight(@RequestBody FlightCreateDTO flightCreateDTO) {
        return flightService.createNewFlight(flightCreateDTO);
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{flightId}")
    public EntityModel<FlightDTO> updateFlight(@PathVariable Long flightId,
                                               @RequestBody FlightCreateDTO flightCreateDTO) {
        return flightService.updateFlight(flightId, flightCreateDTO);
    }
}
