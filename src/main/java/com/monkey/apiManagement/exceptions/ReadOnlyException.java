package com.monkey.apiManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReadOnlyException extends RuntimeException {
    public ReadOnlyException(String message) {
        super(message);
    }
}
