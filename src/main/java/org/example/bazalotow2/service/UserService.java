package org.example.bazalotow2.service;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.user.UserCreateDTO;
import org.example.bazalotow2.dto.user.UserDTO;
import org.example.bazalotow2.entity.User;
import org.example.bazalotow2.hateoas.UserModelAssembler;
import org.example.bazalotow2.mapper.UserMapper;
import org.example.bazalotow2.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserModelAssembler userModelAssembler;
    public static final String simpleClassName = User.class.getSimpleName();

    public EntityModel<UserDTO> register(UserCreateDTO userCreateDTO) {
        User newUser = userMapper.toEntity(userCreateDTO);
        newUser.setAuthority("USER");
        return userModelAssembler.toModel(userRepository.save(newUser));
    }
}
