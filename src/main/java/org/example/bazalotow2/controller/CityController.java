package org.example.bazalotow2.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.city.CityCreateDTO;
import org.example.bazalotow2.dto.city.CityDTO;
import org.example.bazalotow2.service.CityService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

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
}
