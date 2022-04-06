package com.gmail.puhovashablinskaya.service;


import com.gmail.puhovashablinskaya.controller.security.model.AuthRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<Object> authenticateUser(AuthRequest request);
}
