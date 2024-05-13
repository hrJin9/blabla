package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class TokenNotValidException extends BlaBlaException {

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

    public TokenNotValidException(String message) {
        super(message);
    }
}
