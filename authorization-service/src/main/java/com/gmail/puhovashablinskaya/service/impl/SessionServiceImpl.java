package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.SessionRepository;
import com.gmail.puhovashablinskaya.repository.StatusSessionRepository;
import com.gmail.puhovashablinskaya.repository.model.Session;
import com.gmail.puhovashablinskaya.repository.model.StatusEmployeeEnum;
import com.gmail.puhovashablinskaya.repository.model.StatusSession;
import com.gmail.puhovashablinskaya.repository.model.StatusSessionEnum;
import com.gmail.puhovashablinskaya.service.DataTimeService;
import com.gmail.puhovashablinskaya.service.SessionService;
import com.gmail.puhovashablinskaya.service.config.ServiceConfig;
import com.gmail.puhovashablinskaya.service.convert.ConvertSession;
import com.gmail.puhovashablinskaya.service.exception.ServiceExeption;
import com.gmail.puhovashablinskaya.service.model.SessionDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {
    private final ConvertSession convertSession;
    private final DataTimeService dataTimeService;
    private final SessionRepository sessionRepository;
    private final ServiceConfig serviceConfig;
    private final StatusSessionRepository statusSessionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public SessionDTO create(String jwt, String username) {
        Session session = generateSession(jwt, username);
        sessionRepository.add(session);
        return convertSession.convertToDTO(session);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public String findUsernameByJwt(String jwt) {
        return sessionRepository.findUsernameByJwt(jwt);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void closeAllSession(String username) {
        StatusSessionEnum statusEnum = StatusSessionEnum.EXPIRED;
        StatusSession status = generateStatusSession(statusEnum);
        LocalDateTime localDateTime = dataTimeService.currentTimeDate();
        sessionRepository.closeAllSession(username, status, localDateTime);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<SessionDTO> findAllSession(String username) {
        List<Session> sessionList = sessionRepository.findAllSession(username);
        return sessionList.stream()
                .map(convertSession::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean isActiveSession(String jwt) {
        StatusSession statusActive = createStatusActive();
        boolean isActiveSessionBD = sessionRepository.isActiveSession(jwt, statusActive);
        if (!isActiveSessionBD) {
            Optional<Session> activeSession = sessionRepository.findSessionByIdSession(jwt);
            if (activeSession.isPresent()) {
                LocalDateTime dateOfStartSession = activeSession.get().getDateOfStart();
                LocalDateTime dateExist = dateOfStartSession.plus(Duration.ofMillis(serviceConfig.getTokenExpirationMs()));
                LocalDateTime nowTime = dataTimeService.currentTimeDate();
                boolean isActiveSession = (!nowTime.isBefore(dateOfStartSession)) && (nowTime.isBefore(dateExist));
                if (isActiveSession) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean isActiveSessionByUsername(String username) {
        List<Session> allSession = sessionRepository.findAllSession(username);
        String status = StatusEmployeeEnum.ACTIVE.name();
        boolean isExistActiveSession = allSession.stream()
                .anyMatch(session -> session
                        .getStatusSession()
                        .getName()
                        .name()
                        .equals(status));
        if (isExistActiveSession) {
            return true;
        }
        return false;
    }

    private Session generateSession(String jwt, String username) {
        Session session = new Session();
        session.setIdSession(jwt);
        session.setUsername(username);
        session.setDateOfStart(dataTimeService.currentTimeDate());
        session.setStatusSession(createStatusActive());
        return session;
    }

    private StatusSession createStatusActive() {
        StatusSessionEnum statusEnum = StatusSessionEnum.ACTIVE;
        return generateStatusSession(statusEnum);
    }

    private StatusSession generateStatusSession(StatusSessionEnum statusEnum) {
        Optional<StatusSession> statusSession = statusSessionRepository.findByName(statusEnum);
        return statusSession.orElseThrow(() -> new ServiceExeption("Invalid data"));
    }
}