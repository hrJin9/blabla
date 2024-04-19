package com.blabla.exception;

public class LoginBadRequestException extends BadRequestException {
    public LoginBadRequestException(String message) {
        super(message);
    }

    public LoginBadRequestException(Throwable cause) {
        super(cause);
    }

    public LoginBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
