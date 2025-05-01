package com.enigma.spring_fikri_demo.ErrorHandling;

import com.enigma.spring_fikri_demo.dto.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponse<?>> handleResponseStatusException(ResponseStatusException ex) {
        CommonResponse<?> response = new CommonResponse<>(
                ex.getReason(),
                ex.getStatusCode().value(),
                null
        );
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    // Handler untuk Validasi Error (@Valid pada Request Body DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        // Membuat CommonResponse dengan pesan error validasi
        CommonResponse<List<String>> response = new CommonResponse<>(
                "Validation failed",
                HttpStatus.BAD_REQUEST.value(),
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handler untuk Validasi Error Constraint Violation (misalnya, dari path variable atau request param)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        CommonResponse<List<String>> response = new CommonResponse<>(
                "Constraint violation",
                HttpStatus.BAD_REQUEST.value(),
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handler untuk exception umum lainnya (Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleGenericException(Exception ex) {

        CommonResponse<?> response = new CommonResponse<>(
                "An internal server error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}