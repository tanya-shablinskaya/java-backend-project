package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.controller.security.model.AuthRequest;
import com.gmail.puhovashablinskaya.controller.security.model.AuthResponse;
import com.gmail.puhovashablinskaya.controller.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.AuthService;
import com.gmail.puhovashablinskaya.service.SessionService;
import com.gmail.puhovashablinskaya.service.UserService;
import com.gmail.puhovashablinskaya.service.config.MessagesConstants;
import com.gmail.puhovashablinskaya.service.config.ServiceConfig;
import com.gmail.puhovashablinskaya.service.model.SessionDTO;
import com.gmail.puhovashablinskaya.service.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final SessionService sessionService;
    private final ServiceConfig serviceConfig;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<Object> authenticateUser(AuthRequest request) {
        boolean isValidUser = userService.isValidUser(request);
        if (isValidUser) {
            UserDTO user = userService.findUserByUsermailOrUsername(request.getUsername());
            String username = user.getUsername();
            Long userId = user.getId();
            String jwt = jwtUtils.generateJwtToken(username, userId);
            if (jwt != null) {
                SessionDTO currentSession = sessionService.create(jwt, username);
                if (user.getFailedAttempt() > 0) {
                    userService.resetFailedAttempts(username);
                }
                return ResponseEntity.ok(
                        new AuthResponse(
                                currentSession.getSessionId()
                        )
                );
            }
        } else {
            String login = request.getUsername();
            UserDTO userDTO = userService.findUserByUsermailOrUsername(login);
            if (userDTO != null) {
                String username = userDTO.getUsername();
                boolean activeUser = userService.isActiveUser(username);
                if (activeUser && !userDTO.isLockedAccount()) {
                    if (userDTO.getFailedAttempt() < serviceConfig.getMaxFailedAttempts()) {
                        userService.increaseFailedAttempts(userDTO);
                        return ResponseEntity.badRequest().body(Map.of("message", MessagesConstants.INVALID_LOGIN_DATA));
                    } else {
                        userService.lock(userDTO);
                        return ResponseEntity.badRequest()
                                .body(Map.of("message", MessagesConstants.USER_BLOCKED_MESSAGE));
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body(Map.of("message", MessagesConstants.INVALID_LOGIN_DATA));
    }
}
