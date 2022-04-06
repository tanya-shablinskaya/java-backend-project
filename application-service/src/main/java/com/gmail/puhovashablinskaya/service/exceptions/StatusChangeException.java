package com.gmail.puhovashablinskaya.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class StatusChangeException extends RuntimeException {
    public StatusChangeException(String message) {
        super(message);
    }
}
