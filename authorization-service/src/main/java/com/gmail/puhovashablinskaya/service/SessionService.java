package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.SessionDTO;

import java.util.List;

public interface SessionService {
    SessionDTO create(String jwt, String username);

    String findUsernameByJwt(String jwt);

    void closeAllSession(String username);

    List<SessionDTO> findAllSession(String username);

    boolean isActiveSession(String jwt);

    boolean isActiveSessionByUsername(String username);
}
