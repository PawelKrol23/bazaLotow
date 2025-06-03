package org.example.bazalotow2.service;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.airplane.AirplaneCreateDTO;
import org.example.bazalotow2.dto.airplane.AirplaneDTO;
import org.example.bazalotow2.entity.Airplane;
import org.example.bazalotow2.exception.notfound.EntityNotFoundException;
import org.example.bazalotow2.hateoas.AirplaneModelAssembler;
import org.example.bazalotow2.mapper.AirplaneMapper;
import org.example.bazalotow2.repository.AirplaneRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final AirplaneModelAssembler airplaneModelAssembler;
    private final AirplaneMapper airplaneMapper;
    public static final String simpleClassName = Airplane.class.getSimpleName();

    public EntityModel<AirplaneDTO> getAirplaneById(long airplaneId) {
        Airplane foundAirplane = airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new EntityNotFoundException(airplaneId, simpleClassName));
        return airplaneModelAssembler.toModel(foundAirplane);
    }

    public CollectionModel<EntityModel<AirplaneDTO>> getAllAirplanes() {
        List<Airplane> airplanes = airplaneRepository.findAll();
        return airplaneModelAssembler.toCollectionModel(airplanes);
    }

    public EntityModel<AirplaneDTO> createNewAirplane(AirplaneCreateDTO airplaneCreateDTO) {
        Airplane newAirplane = airplaneRepository.save(airplaneMapper.toEntity(airplaneCreateDTO));
        return airplaneModelAssembler.toModel(newAirplane);
    }

    public void deleteAirplane(Long airplaneId) {
        Airplane foundAirplane = airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new EntityNotFoundException(airplaneId, simpleClassName));
        airplaneRepository.delete(foundAirplane);
    }

    public EntityModel<AirplaneDTO> updateAirplane(Long airplaneId,
                                                   AirplaneCreateDTO airplaneCreateDTO) {
        Airplane foundAirplane = airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new EntityNotFoundException(airplaneId, simpleClassName));

        foundAirplane.setCapacity(airplaneCreateDTO.capacity());
        foundAirplane.setModel(airplaneCreateDTO.model());
        foundAirplane.setRegistrationNumber(airplaneCreateDTO.registrationNumber());

        return airplaneModelAssembler.toModel(airplaneRepository.save(foundAirplane));
    }
}
