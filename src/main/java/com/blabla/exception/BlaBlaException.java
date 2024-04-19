package com.blabla.exception;

public class BlaBlaException extends RuntimeException {
    public BlaBlaException(String message) {
        super(message);
    }

    public BlaBlaException(Throwable cause) {
        super(cause);
    }

    public BlaBlaException(String message, Throwable cause) {
        super(message, cause);
    }
}
