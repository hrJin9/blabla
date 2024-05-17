package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class BoardUnAuthorizedException extends BlaBlaException {
    @Override
    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }

    public BoardUnAuthorizedException(String message) {
        super(message);
    }
}
