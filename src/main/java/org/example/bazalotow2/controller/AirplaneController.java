package org.example.bazalotow2.controller;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.airplane.AirplaneCreateDTO;
import org.example.bazalotow2.dto.airplane.AirplaneDTO;
import org.example.bazalotow2.dto.flight.FlightDTO;
import org.example.bazalotow2.service.AirplaneService;
import org.example.bazalotow2.service.FlightService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airplane")
@RequiredArgsConstructor
public class AirplaneController {
    private final AirplaneService airplaneService;
    private final FlightService flightService;

    @GetMapping("/{airplaneId}")
    public EntityModel<AirplaneDTO> getAirplaneById(@PathVariable Long airplaneId) {
        return airplaneService.getAirplaneById(airplaneId);
    }

    @GetMapping
    public CollectionModel<EntityModel<AirplaneDTO>> getAllAirplanes() {
        return airplaneService.getAllAirplanes();
    }

    @PostMapping
    public EntityModel<AirplaneDTO> createNewAirplane(@RequestBody AirplaneCreateDTO airplaneCreateDTO) {
        return airplaneService.createNewAirplane(airplaneCreateDTO);
    }

    @DeleteMapping("/{airplaneId}")
    public ResponseEntity<Void> deleteAirplane(@PathVariable Long airplaneId) {
        airplaneService.deleteAirplane(airplaneId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{airplaneId}")
    public EntityModel<AirplaneDTO> updateAirplane(@PathVariable Long airplaneId,
                                                   @RequestBody AirplaneCreateDTO airplaneCreateDTO) {
        return airplaneService.updateAirplane(airplaneId, airplaneCreateDTO);
    }

    @GetMapping("/{airplaneId}/flights")
    public CollectionModel<EntityModel<FlightDTO>> getAirplanesFlights(@PathVariable Long airplaneId) {
        return flightService.getFlightsByAirplaneId(airplaneId);
    }
}
