package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.controller.security.model.AuthRequest;
import com.gmail.puhovashablinskaya.controller.security.model.AuthResponse;
import com.gmail.puhovashablinskaya.controller.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.SessionService;
import com.gmail.puhovashablinskaya.service.UserService;
import com.gmail.puhovashablinskaya.service.config.ServiceConfig;
import com.gmail.puhovashablinskaya.service.model.SessionDTO;
import com.gmail.puhovashablinskaya.service.model.StatusSessionEnumDTO;
import com.gmail.puhovashablinskaya.service.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserService userService;
    @Mock
    private SessionService sessionService;
    @Mock
    private ServiceConfig serviceConfig;
    @Mock
    private JwtUtils jwtUtils;

    private final static String JWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTYWFhYWFhYWFhYSIsInVzZXJJZCI6NiwiY2hlY2tVc2VySWQiOnRydWUsImlhdCI6MTY0ODM4NTk1MiwiZXhwIjoxNjk4OTc1MTkyfQ.bMK1yNXx-OXHbwW2zzURLlwjUf-l0Wya7Kd4DrSsN-DC97VgvhQdP0YO2BtcgeZ-votX4cwzar7--xp4qd_tYg";

    @Test
    void shouldReturnValidUser() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        when(userService.isValidUser(authRequest)).thenReturn(true);
        UserDTO userDTO = findUserDTO();
        when(userService.findUserByUsermailOrUsername(authRequest.getUsername())).thenReturn(userDTO);
        when(jwtUtils.generateJwtToken(userDTO.getUsername(), userDTO.getId())).thenReturn(JWT);
        when(sessionService.create(JWT, userDTO.getUsername())).thenReturn(findSessionDTO());
        ResponseEntity<Object> response = authService.authenticateUser(authRequest);
        Assertions.assertEquals(response, ResponseEntity.ok(new AuthResponse(JWT)));
    }

    @Test
    void shouldReturnIsNotValidUser() {
        AuthRequest authRequest = new AuthRequest("username", "passwor");
        UserDTO userDTO = findUserDTO();
        when(userService.isValidUser(authRequest)).thenReturn(false);
        when(userService.findUserByUsermailOrUsername(authRequest.getUsername())).thenReturn(userDTO);
        when(userService.isActiveUser(userDTO.getUsername())).thenReturn(true);
        ResponseEntity<Object> responseEntity = authService.authenticateUser(authRequest);
        Assertions.assertEquals(responseEntity, ResponseEntity.badRequest().body(Map.of("message", "Ваша учетная запись заблокирована. Обратитесь к Администратору")));
    }

    @Test
    void shouldReturnIsNotActiveUser() {
        AuthRequest authRequest = new AuthRequest("username", "passwor");
        UserDTO userDTO = findUserDTO();
        when(userService.isValidUser(authRequest)).thenReturn(false);
        when(userService.findUserByUsermailOrUsername(authRequest.getUsername())).thenReturn(userDTO);
        when(userService.isActiveUser(userDTO.getUsername())).thenReturn(false);
        ResponseEntity<Object> responseEntity = authService.authenticateUser(authRequest);
        Assertions.assertEquals(responseEntity, ResponseEntity.badRequest().body(Map.of("message", "Login or password is not valid")));
    }

    @Test
    void shouldReturnIsJwtNull() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        UserDTO userDTO = findUserDTO();
        when(userService.isValidUser(authRequest)).thenReturn(true);
        when(userService.findUserByUsermailOrUsername(authRequest.getUsername())).thenReturn(userDTO);
        when(jwtUtils.generateJwtToken(userDTO.getUsername(), userDTO.getId())).thenReturn(null);
        ResponseEntity<Object> responseEntity = authService.authenticateUser(authRequest);
        Assertions.assertEquals(responseEntity, ResponseEntity.badRequest().body(Map.of("message", "Login or password is not valid")));
    }


    private SessionDTO findSessionDTO() {
        return SessionDTO.builder()
                .sessionId(JWT)
                .username("username")
                .statusSession(StatusSessionEnumDTO.ACTIVE)
                .dateOfStart(LocalDateTime.now())
                .build();
    }

    private UserDTO findUserDTO() {
        return UserDTO.builder()
                .id(1L)
                .username("username")
                .password("password")
                .usermail("viexdg@gmail.com")
                .firstName("Бимбус")
                .dateOfCreate(LocalDateTime.now())
                .failedAttempt(0)
                .lockedAccount(false)
                .build();
    }

}