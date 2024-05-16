package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class LikesNotFoundException extends BlaBlaException {

    @Override
    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }

    public LikesNotFoundException(String message) {
        super(message);
    }
}
