package org.example.bazalotow2.mapper;

import org.example.bazalotow2.dto.user.UserCreateDTO;
import org.example.bazalotow2.dto.user.UserDTO;
import org.example.bazalotow2.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserCreateDTO createDTO);
}
