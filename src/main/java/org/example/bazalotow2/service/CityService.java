package org.example.bazalotow2.service;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
}
