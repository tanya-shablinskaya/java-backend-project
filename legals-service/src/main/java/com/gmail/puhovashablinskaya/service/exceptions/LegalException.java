package com.gmail.puhovashablinskaya.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LegalException extends RuntimeException {
    public LegalException(String message) {
        super(message);
    }
}
