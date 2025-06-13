package org.example.bazalotow2.controller;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.dto.user.UserCreateDTO;
import org.example.bazalotow2.dto.user.UserDTO;
import org.example.bazalotow2.service.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public EntityModel<UserDTO> register(@RequestBody UserCreateDTO userCreateDTO) {
        return userService.register(userCreateDTO);
    }
}
