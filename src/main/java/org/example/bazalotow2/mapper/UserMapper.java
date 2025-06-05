package org.example.bazalotow2.mapper;

import org.example.bazalotow2.dto.user.UserDTO;
import org.example.bazalotow2.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO toDto(User user);
}
