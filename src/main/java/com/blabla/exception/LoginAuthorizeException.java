package com.blabla.exception;

public class LoginAuthorizeException extends UnAuthorizedException {
    public LoginAuthorizeException(String message) {
        super(message);
    }

    public LoginAuthorizeException(Throwable cause) {
        super(cause);
    }

    public LoginAuthorizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
