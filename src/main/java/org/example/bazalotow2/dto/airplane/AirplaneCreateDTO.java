package org.example.bazalotow2.dto.airplane;

public record AirplaneCreateDTO(
    String model,
    String registrationNumber,
    int capacity
) {
}
