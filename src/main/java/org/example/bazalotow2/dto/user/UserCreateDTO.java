package org.example.bazalotow2.dto.user;

public record UserCreateDTO(
    String email,
    String password,
    String firstName,
    String lastName
) {
}
