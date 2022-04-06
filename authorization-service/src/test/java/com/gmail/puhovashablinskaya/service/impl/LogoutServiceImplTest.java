package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.controller.security.model.LogoutRequest;
import com.gmail.puhovashablinskaya.service.EmployeeDetailsService;
import com.gmail.puhovashablinskaya.service.SessionService;
import com.gmail.puhovashablinskaya.service.model.SessionDTO;
import com.gmail.puhovashablinskaya.service.model.StatusSessionEnumDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LogoutServiceImplTest {
    @InjectMocks
    private LogoutServiceImpl logoutService;
    @Mock
    private SessionService sessionService;
    @Mock
    private EmployeeDetailsService detailsService;

    @Test
    void shouldReturnValidInfo() {
        LogoutRequest logoutRequest = new LogoutRequest("username", "qqqqqqwqeeqwrwr");
        when(sessionService.isActiveSessionByUsername(logoutRequest.getUsername())).thenReturn(true);
        ResponseEntity<HttpStatus> response = logoutService.logoutEmployee(logoutRequest);
        Assertions.assertEquals(response, ResponseEntity.ok(HttpStatus.OK));
    }

    @Test
    void shouldReturnWhenIsNotActiveSessionExist() {
        LogoutRequest logoutRequest = new LogoutRequest("username", "qqqqqqwqeeqwrwr");
        SessionDTO sessionDTO = SessionDTO.builder()
                .sessionId("qqqqqqwqeeqwrwr")
                .username("username")
                .statusSession(StatusSessionEnumDTO.EXPIRED)
                .dateOfStart(LocalDateTime.MAX)
                .build();
        when(sessionService.isActiveSessionByUsername(logoutRequest.getUsername())).thenReturn(false);
        when(sessionService.findAllSession(logoutRequest.getUsername())).thenReturn(List.of(sessionDTO));
        ResponseEntity<HttpStatus> response = logoutService.logoutEmployee(logoutRequest);
        Assertions.assertEquals(response, new ResponseEntity(HttpStatus.UNAUTHORIZED));
    }

    @Test
    void shouldReturnWhenUserUnauthorized() {
        LogoutRequest logoutRequest = new LogoutRequest("username", "qqqqqqwqeeqwrwr");
        SessionDTO sessionDTO = SessionDTO.builder().build();
        when(sessionService.isActiveSessionByUsername(logoutRequest.getUsername())).thenReturn(false);
        when(sessionService.findAllSession(logoutRequest.getUsername())).thenReturn(List.of(sessionDTO));
        ResponseEntity<HttpStatus> response = logoutService.logoutEmployee(logoutRequest);
        Assertions.assertEquals(response, new ResponseEntity(HttpStatus.UNAUTHORIZED));
    }

    @Test
    void shouldReturnWhenAllSessionisEmpty() {
        LogoutRequest logoutRequest = new LogoutRequest("username", "qqqqqqwqeeqwrwr");
        List<SessionDTO> sessionDTO = new ArrayList<>();
        when(sessionService.isActiveSessionByUsername(logoutRequest.getUsername())).thenReturn(false);
        when(sessionService.findAllSession(logoutRequest.getUsername())).thenReturn(sessionDTO);
        ResponseEntity<HttpStatus> response = logoutService.logoutEmployee(logoutRequest);
        Assertions.assertEquals(response, new ResponseEntity(HttpStatus.UNAUTHORIZED));
    }
}