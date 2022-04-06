package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.controller.security.model.LogoutRequest;
import com.gmail.puhovashablinskaya.service.EmployeeDetailsService;
import com.gmail.puhovashablinskaya.service.LogoutService;
import com.gmail.puhovashablinskaya.service.SessionService;
import com.gmail.puhovashablinskaya.service.model.SessionDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LogoutServiceImpl implements LogoutService {
    private final SessionService sessionService;
    private final EmployeeDetailsService detailsService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<HttpStatus> logoutEmployee(LogoutRequest logout) {
        String username = logout.getUsername();
        boolean isActiveSessionExist = sessionService.isActiveSessionByUsername(username);
        if (isActiveSessionExist) {
            sessionService.closeAllSession(username);
            detailsService.saveDateOfLogout(username);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        List<SessionDTO> allSession = sessionService.findAllSession(username);
        if (allSession.isEmpty()) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
