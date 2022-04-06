package com.gmail.puhovashablinskaya.controller.security.model;

import com.gmail.puhovashablinskaya.service.validators.ValidLogoutRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ValidLogoutRequest
public class LogoutRequest {
    private String username;
    private String sessionId;
}

