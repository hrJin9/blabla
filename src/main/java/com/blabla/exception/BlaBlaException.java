package com.blabla.exception;

import org.springframework.http.HttpStatus;

public abstract class BlaBlaException extends RuntimeException {
    public abstract HttpStatus status();

    public BlaBlaException(String message) {
        super(message);
    }
}
