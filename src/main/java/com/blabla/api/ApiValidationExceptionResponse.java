package com.blabla.api;

public record ApiValidationExceptionResponse (
        String field,
        String message
) {

    public static ApiValidationExceptionResponse of(String field, String message) {
        return new ApiValidationExceptionResponse(field, message);
    }
}
