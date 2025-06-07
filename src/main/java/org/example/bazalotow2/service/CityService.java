package org.example.bazalotow2.service;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.city.CityCreateDTO;
import org.example.bazalotow2.dto.city.CityDTO;
import org.example.bazalotow2.entity.City;
import org.example.bazalotow2.exception.notfound.EntityNotFoundException;
import org.example.bazalotow2.hateoas.CityModelAssembler;
import org.example.bazalotow2.mapper.CityMapper;
import org.example.bazalotow2.repository.CityRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CityModelAssembler cityModelAssembler;
    private final CityMapper cityMapper;
    public static final String simpleClassName = City.class.getSimpleName();

    public EntityModel<CityDTO> getCityById(long cityId) {
        var foundCity = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException(cityId, simpleClassName));
        return cityModelAssembler.toModel(foundCity);
    }

    public CollectionModel<EntityModel<CityDTO>> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cityModelAssembler.toCollectionModel(cities);
    }


    public EntityModel<CityDTO> createNewCity(CityCreateDTO cityCreateDTO) {
        City newCity = cityRepository.save(cityMapper.toEntity(cityCreateDTO));
        return cityModelAssembler.toModel(newCity);
    }

    public void deleteCity(Long cityId) {
        City foundCity = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException(cityId, simpleClassName));
        cityRepository.delete(foundCity);
    }

    public EntityModel<CityDTO> updateCity(Long cityId, CityCreateDTO cityCreateDTO) {
        City foundCity = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException(cityId, simpleClassName));

        foundCity.setName(cityCreateDTO.name());
        foundCity.setCountry(cityCreateDTO.country());

        return cityModelAssembler.toModel(cityRepository.save(foundCity));
    }
}
