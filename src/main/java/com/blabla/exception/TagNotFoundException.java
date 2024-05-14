package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class TagNotFoundException extends BlaBlaException {

    @Override
    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }

    public TagNotFoundException(String message) {
        super(message);
    }
}
