package org.example.bazalotow2.controller;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
