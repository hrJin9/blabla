package com.blabla.exception;

public class BoardNotExistsException extends BlaBlaException {
    public BoardNotExistsException(String message) {
        super(message);
    }

    public BoardNotExistsException(Throwable cause) {
        super(cause);
    }

    public BoardNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
