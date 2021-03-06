package com.test.contactpost.controller;

import com.test.contactpost.exception.ContactNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new Body(HttpStatus.BAD_REQUEST.value(),
                "Id must be at least 1"), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new Body(HttpStatus.BAD_REQUEST.value(),
                "Id must be numeric"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<Body> handleContactNotFoundException(
            ContactNotFoundException ex) {
        return new ResponseEntity<>(new Body(HttpStatus.NOT_FOUND.value(),
                ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Body> handleIllegalArgumentException() {
        return new ResponseEntity<>(new Body(HttpStatus.BAD_REQUEST.value(),
                "Please, enter correct Id"), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class Body {
        private Integer errorCode;
        private String errorMessage;
    }
}
