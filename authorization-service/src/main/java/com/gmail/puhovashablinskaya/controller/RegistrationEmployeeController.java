package com.gmail.puhovashablinskaya.controller;

import com.gmail.puhovashablinskaya.service.UserService;
import com.gmail.puhovashablinskaya.service.model.UserAddDTO;
import com.gmail.puhovashablinskaya.service.model.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationEmployeeController {
    private final UserService userService;

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO addUser(@RequestBody @Validated UserAddDTO user) {
        return userService.add(user);
    }
}
