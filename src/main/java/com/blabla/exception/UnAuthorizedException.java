package com.blabla.exception;

public class UnAuthorizedException extends BlaBlaException {
    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(Throwable cause) {
        super(cause);
    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
