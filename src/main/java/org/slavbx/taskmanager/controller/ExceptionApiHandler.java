package org.slavbx.taskmanager.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionApiHandler {

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException exception) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new ErrorMessage(exception.getMessage()));
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> notValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("message: " + exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintException(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("message: " + exception.getMessage());
    }

}
