package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends BlaBlaException {

    @Override
    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
