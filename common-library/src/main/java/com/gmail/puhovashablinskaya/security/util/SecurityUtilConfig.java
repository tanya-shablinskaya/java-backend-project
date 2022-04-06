package com.gmail.puhovashablinskaya.security.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("classpath:applicationJwt.properties")
public class SecurityUtilConfig {
    @Value("${jwt.system}")
    private String jwtSystem;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.claims.user.id}")
    private String userId;

    @Value("${jwt.claims.check.user.id}")
    private String checkUserId;
}
