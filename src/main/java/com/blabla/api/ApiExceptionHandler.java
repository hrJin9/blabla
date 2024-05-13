package com.blabla.api;

import com.blabla.exception.BlaBlaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BlaBlaException.class)
    public ResponseEntity<ApiExceptionResponse> handleBlaBlaException(BlaBlaException exception) {

        log.error("status = {}, message = {}", exception.status() , exception.getMessage());
        return ResponseEntity
                .status(exception.status())
                .body(new ApiExceptionResponse(exception.getMessage()));
    }
}
