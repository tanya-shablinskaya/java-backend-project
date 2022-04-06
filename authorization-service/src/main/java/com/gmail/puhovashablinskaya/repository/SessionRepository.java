package com.gmail.puhovashablinskaya.repository;


import com.gmail.puhovashablinskaya.repository.model.Session;
import com.gmail.puhovashablinskaya.repository.model.StatusSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends GenericRepository<Long, Session> {

    Optional<Session> findSessionByIdSession(String sessionId);

    void updateStatusSessionById(String sessionID, StatusSession statusSession);

    void closeAllSession(String username, StatusSession statusSession, LocalDateTime currentTime);

    List<Session> findAllSession(String username);

    boolean isActiveSession(String jwt, StatusSession statusActive);

    String findUsernameByJwt(String jwt);
}
