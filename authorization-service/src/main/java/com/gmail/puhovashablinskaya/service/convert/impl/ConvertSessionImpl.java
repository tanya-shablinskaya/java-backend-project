package com.gmail.puhovashablinskaya.service.convert.impl;

import com.gmail.puhovashablinskaya.repository.StatusSessionRepository;
import com.gmail.puhovashablinskaya.repository.model.Session;
import com.gmail.puhovashablinskaya.repository.model.StatusSession;
import com.gmail.puhovashablinskaya.repository.model.StatusSessionEnum;
import com.gmail.puhovashablinskaya.service.convert.ConvertSession;
import com.gmail.puhovashablinskaya.service.model.SessionDTO;
import com.gmail.puhovashablinskaya.service.model.StatusSessionEnumDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class ConvertSessionImpl implements ConvertSession {
    private final StatusSessionRepository statusRepository;

    @Override
    public Session convertToSession(SessionDTO sessionDTO) {
        Session session = new Session();
        session.setIdSession(sessionDTO.getSessionId());
        session.setUsername(session.getUsername());
        session.setDateOfStart(sessionDTO.getDateOfStart());
        Optional<StatusSession> status = findStatus(sessionDTO.getStatusSession());
        status.ifPresent(session::setStatusSession);
        return session;
    }

    private Optional<StatusSession> findStatus(StatusSessionEnumDTO statusSession) {
        String statusString = statusSession.name();
        StatusSessionEnum statusSessionEnum = StatusSessionEnum.valueOf(statusString);
        return statusRepository.findByName(statusSessionEnum);
    }

    @Override
    public SessionDTO convertToDTO(Session session) {
        return SessionDTO.builder()
                .sessionId(session.getIdSession())
                .username(session.getUsername())
                .dateOfStart(session.getDateOfStart())
                .dateOfFinish(session.getDateOfFinish())
                .statusSession(findStatusDTO(session.getStatusSession()))
                .build();
    }

    private StatusSessionEnumDTO findStatusDTO(StatusSession statusSession) {
        String statusString = statusSession.getName().name();
        return StatusSessionEnumDTO.valueOf(statusString);
    }
}
