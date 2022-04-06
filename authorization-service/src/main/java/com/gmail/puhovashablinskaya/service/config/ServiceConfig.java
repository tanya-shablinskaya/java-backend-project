package com.gmail.puhovashablinskaya.service.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServiceConfig {
    @Value("${time.zone}")
    private String timeZone;

    @Value("${format.date-time}")
    private String formatDate;

    @Value("${jwt.expiration.ms}")
    private Integer tokenExpirationMs;

    @Value("${regex.first.name.rus.char}")
    private String regexNameRusChar;

    @Value("${first.name.max.length}")
    private Integer maxLengthFirstName;

    @Value("${max.failed.attempts}")
    private Integer maxFailedAttempts;
}


