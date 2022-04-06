package com.gmail.puhovashablinskaya.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaginationException extends RuntimeException {
    public PaginationException(String message) {
        super(message);
    }
}
