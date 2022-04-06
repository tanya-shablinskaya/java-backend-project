package com.gmail.puhovashablinskaya.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmployeeUniqueException extends RuntimeException {
    public EmployeeUniqueException(String message) {
        super(message);
    }
}
