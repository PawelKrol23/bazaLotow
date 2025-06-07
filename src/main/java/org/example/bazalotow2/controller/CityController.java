package org.example.bazalotow2.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.city.CityCreateDTO;
import org.example.bazalotow2.dto.city.CityDTO;
import org.example.bazalotow2.dto.flight.FlightDTO;
import org.example.bazalotow2.service.CityService;
import org.example.bazalotow2.service.FlightService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
    private final FlightService flightService;

    @GetMapping("/{cityId}")
    public EntityModel<CityDTO> getCityById(@PathVariable Long cityId) {
        return cityService.getCityById(cityId);
    }

    @GetMapping
    public CollectionModel<EntityModel<CityDTO>> getAllCities() {
        return cityService.getAllCities();
    }

    @PostMapping
    @SecurityRequirement(name = "basicAuth")
    public EntityModel<CityDTO> createNewCity(@RequestBody CityCreateDTO cityCreateDTO) {
        return cityService.createNewCity(cityCreateDTO);
    }

    @DeleteMapping("/{cityId}")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<Void> deleteCity(@PathVariable Long cityId) {
        cityService.deleteCity(cityId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cityId}")
    @SecurityRequirement(name = "basicAuth")
    public EntityModel<CityDTO> updateCity(@PathVariable Long cityId,
                                           @RequestBody CityCreateDTO cityCreateDTO) {
        return cityService.updateCity(cityId, cityCreateDTO);
    }

    @GetMapping("/{cityId}/departingFlights")
    public CollectionModel<EntityModel<FlightDTO>> getDepartingFlights(@PathVariable Long cityId) {
        return flightService.getFlightsDepartingFrom(cityId);
    }

    @GetMapping("/{cityId}/arrivingFlights")
    public CollectionModel<EntityModel<FlightDTO>> getArrivingFlights(@PathVariable Long cityId) {
        return flightService.getFlightsArrivingTo(cityId);
    }
}
