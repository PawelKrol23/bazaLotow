package org.example.bazalotow2.controller;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.service.CityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
}
