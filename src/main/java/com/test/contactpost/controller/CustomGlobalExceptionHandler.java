package com.test.contactpost.controller;

import com.test.contactpost.exception.ContactNotFoundException;
import javax.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<Body> handleContactNotFoundException(
            ContactNotFoundException ex) {
        return new ResponseEntity<>(new Body(HttpStatus.NOT_FOUND.value(),
                ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Body> handleConstraintViolationException(
            ConstraintViolationException ex) {
        return new ResponseEntity<>(new Body(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new Body(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Body> handleMethodArgumentTypeMismatchException() {
        return new ResponseEntity<>(new Body(HttpStatus.BAD_REQUEST.value(),
                "Please, enter correct id"), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class Body {
        private int errorCode;
        private String errorMessage;
    }
}
