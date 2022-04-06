package com.gmail.puhovashablinskaya.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SearchInputException extends RuntimeException {
    public SearchInputException(String message) {
        super(message);
    }
}
