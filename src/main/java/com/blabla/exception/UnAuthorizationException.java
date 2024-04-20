package com.blabla.exception;

public class UnAuthorizationException extends BlaBlaException {
    public UnAuthorizationException(String message) {
        super(message);
    }

    public UnAuthorizationException(Throwable cause) {
        super(cause);
    }

    public UnAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
