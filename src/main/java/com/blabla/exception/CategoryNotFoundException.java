package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BlaBlaException {

    @Override
    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
