package com.gmail.puhovashablinskaya.controller.security.model;

import com.gmail.puhovashablinskaya.service.validators.ValidAuthRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ValidAuthRequest
public class AuthRequest {
    private String username;
    private String password;
}

