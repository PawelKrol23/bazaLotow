package org.example.bazalotow2.dto;

public record ErrorDTO(
    String timestamp,
    String message,
    String details
) {
}
