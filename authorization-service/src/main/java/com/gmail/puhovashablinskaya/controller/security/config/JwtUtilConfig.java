package com.gmail.puhovashablinskaya.controller.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtUtilConfig {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms}")
    private int jwtExpirationMs;

    @Value("${jwt.claims.user.id}")
    private String userId;

    @Value("${jwt.claims.check.user.id}")
    private String checkUserId;

    @Value("${jwt.check.user.default.boolean}")
    private Boolean isCheckUser;
}


