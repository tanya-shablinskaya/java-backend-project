package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.controller.security.model.LogoutRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface LogoutService {
    ResponseEntity<HttpStatus> logoutEmployee(LogoutRequest logout);
}
