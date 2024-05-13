package com.blabla.exception;

import org.springframework.http.HttpStatus;

public class LoginUnAuthorizedException extends BlaBlaException {
    @Override
    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }

    public LoginUnAuthorizedException(String message) {
        super(message);
    }
}
