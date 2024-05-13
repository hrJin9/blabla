package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends BlaBlaException {

    @Override
    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }

    public BoardNotFoundException(String message) {
        super(message);
    }
}
