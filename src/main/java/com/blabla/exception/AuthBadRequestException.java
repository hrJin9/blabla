package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class AuthBadRequestException extends BlaBlaException {

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

    public AuthBadRequestException(String message) {
        super(message);
    }
}
