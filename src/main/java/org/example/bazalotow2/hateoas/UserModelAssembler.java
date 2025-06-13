package org.example.bazalotow2.hateoas;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.user.UserDTO;
import org.example.bazalotow2.entity.User;
import org.example.bazalotow2.mapper.UserMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<UserDTO>> {
    private final UserMapper userMapper;

    @Override
    public EntityModel<UserDTO> toModel(User user) {
        return EntityModel.of(userMapper.toDto(user));
    }
}
