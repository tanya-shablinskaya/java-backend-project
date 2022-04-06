package com.gmail.puhovashablinskaya.controller;

import com.gmail.puhovashablinskaya.service.SessionService;
import com.gmail.puhovashablinskaya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class SessionsController {
    private final SessionService sessionService;
    private final UserService userService;

    @PostMapping(value = "/sessions", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Object> checkJwt(@RequestBody String jwt) {
        if (jwt != null) {
            boolean activeSession = sessionService.isActiveSession(jwt);
            String usernameByJwt = sessionService.findUsernameByJwt(jwt);
            boolean activeUser = userService.isActiveUser(usernameByJwt);
            if (activeSession && activeUser) {
                return ResponseEntity.ok(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    }
}

