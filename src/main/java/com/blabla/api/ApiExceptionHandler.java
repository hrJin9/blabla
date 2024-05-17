package com.blabla.api;

import com.blabla.exception.BlaBlaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ApiValidationExceptionResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        log.error("status = {}, message = {}", exception.getStatusCode(), exception.getMessage());
        BindingResult bindingResult = exception .getBindingResult();

        List<ApiValidationExceptionResponse> response = bindingResult.getFieldErrors().stream()
                .map((fieldError) -> ApiValidationExceptionResponse.of(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(response);
    }
}
