package com.gmail.puhovashablinskaya.service.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AuthRequestValidatorConfig {
    @Value("${login.name.min.length}")
    private Integer loginNameMinLength;

    @Value("${login.name.max.length}")
    private Integer loginNameMaxLength;

    @Value("${password.min.length}")
    private Integer passwordMinLength;

    @Value("${password.max.length}")
    private Integer passwordMaxLength;

    @Value("${mail.max.length}")
    private Integer mailMaxLength;

    @Value("${regex.format.mail}")
    private String regexFormatMail;

    @Value("${regex.login.name.latine.char}")
    private String regexLoginNameLatineChar;

    @Value("${regex.login.name.char.lower.case}")
    private String regexLoginNameCharLowerCase;

    @Value("${regex.login.name.digits}")
    private String regexLoginNameDigits;

    @Value("${regex.first.name.rus.char}")
    private String regexFirstNameRusChar;
}


