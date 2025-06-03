package org.example.bazalotow2.dto.airplane;

public record AirplaneDTO(
    long id,
    String model,
    String registrationNumber,
    int capacity
) {
}
