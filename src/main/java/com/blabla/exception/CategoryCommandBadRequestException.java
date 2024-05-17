package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class CategoryCommandBadRequestException extends BlaBlaException {

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

    public CategoryCommandBadRequestException(String message) {
        super(message);
    }
}
