package com.gmail.puhovashablinskaya.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LegalNotUniqueException extends RuntimeException {
    public LegalNotUniqueException(String message) {
        super(message);
    }
}
