package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.SessionRepository;
import com.gmail.puhovashablinskaya.repository.StatusSessionRepository;
import com.gmail.puhovashablinskaya.repository.model.Session;
import com.gmail.puhovashablinskaya.repository.model.StatusSession;
import com.gmail.puhovashablinskaya.repository.model.StatusSessionEnum;
import com.gmail.puhovashablinskaya.service.DataTimeService;
import com.gmail.puhovashablinskaya.service.config.ServiceConfig;
import com.gmail.puhovashablinskaya.service.convert.ConvertSession;
import com.gmail.puhovashablinskaya.service.model.SessionDTO;
import com.gmail.puhovashablinskaya.service.model.StatusSessionEnumDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {
    @InjectMocks
    private SessionServiceImpl sessionService;
    @Mock
    private ConvertSession convertSession;
    @Mock
    private DataTimeService dataTimeService;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private ServiceConfig serviceConfig;
    @Mock
    private StatusSessionRepository statusSessionRepository;

    private final static String JWT = "qweqwesgdg";
    private final static String USERNAME = "username";

    @Test
    void shouldReturnCreateSession() {
        Session session = findSession();
        when(convertSession.convertToDTO(session)).thenReturn(findSessionDTO());
        StatusSession statusSession = new StatusSession();
        statusSession.setId(1L);
        statusSession.setName(StatusSessionEnum.ACTIVE);
        when(statusSessionRepository.findByName(StatusSessionEnum.ACTIVE)).thenReturn(Optional.of(statusSession));
        SessionDTO response = sessionService.create(JWT, USERNAME);
        Assertions.assertEquals(response, findSessionDTO());
    }

    @Test
    void shouldReturnFindUsernameByJwt() {
        when(sessionRepository.findUsernameByJwt(JWT)).thenReturn(USERNAME);
        String response = sessionService.findUsernameByJwt(JWT);
        Assertions.assertEquals(response, USERNAME);
    }

    @Test
    void shouldReturnFindAllSession() {
        when(sessionRepository.findAllSession(USERNAME)).thenReturn(List.of(findSession()));
        when(convertSession.convertToDTO(findSession())).thenReturn(findSessionDTO());
        List<SessionDTO> response = sessionService.findAllSession(USERNAME);
        Assertions.assertEquals(response, List.of(findSessionDTO()));
    }

    @Test
    void shouldReturnWhenIsActiveSession() {
        StatusSession statusSession = new StatusSession();
        statusSession.setId(1L);
        statusSession.setName(StatusSessionEnum.ACTIVE);
        when(sessionRepository.isActiveSession(JWT, createStatusActive())).thenReturn(true);
        when(statusSessionRepository.findByName(StatusSessionEnum.ACTIVE)).thenReturn(Optional.of(statusSession));
        boolean response = sessionService.isActiveSession(JWT);
        Assertions.assertEquals(response, false);
    }

    @Test
    void shouldReturnWhenNotActiveSession() {
        when(sessionRepository.findAllSession(USERNAME)).thenReturn(List.of(findSession()));
        boolean response = sessionService.isActiveSessionByUsername(USERNAME);
        Assertions.assertEquals(response, Boolean.TRUE);
    }

    private Session findSessionExpired() {
        Session session = new Session();
        session.setIdSession(JWT);
        session.setUsername(USERNAME);
        session.setDateOfStart(null);
        session.setStatusSession(createStatusExpired());
        return session;
    }

    private StatusSession createStatusExpired() {
        StatusSession statusSession = new StatusSession();
        StatusSessionEnum statusEnum = StatusSessionEnum.EXPIRED;
        statusSession.setName(statusEnum);
        statusSession.setId(2L);
        return statusSession;
    }

    private SessionDTO findSessionDTO() {
        return SessionDTO.builder()
                .sessionId(JWT)
                .username("username")
                .statusSession(StatusSessionEnumDTO.ACTIVE)
                .dateOfStart(null)
                .build();
    }

    private Session findSession() {
        Session session = new Session();
        session.setIdSession(JWT);
        session.setUsername(USERNAME);
        session.setDateOfStart(null);
        session.setStatusSession(createStatusActive());
        return session;
    }

    private StatusSession createStatusActive() {
        StatusSession statusSession = new StatusSession();
        StatusSessionEnum statusEnum = StatusSessionEnum.ACTIVE;
        statusSession.setName(statusEnum);
        statusSession.setId(1L);
        return statusSession;
    }
}