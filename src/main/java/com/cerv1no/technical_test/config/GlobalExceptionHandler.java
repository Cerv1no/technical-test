package com.cerv1no.technical_test.config;

import com.cerv1no.technical_test.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());

        List<Map<String, String>> violations = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    Map<String, String> error = new HashMap<>();
                    String path = violation.getPropertyPath().toString();
                    error.put("field", path.substring(path.indexOf('.') + 1));
                    error.put("message", violation.getMessage());
                    return error;
                })
                .toList();

        response.put("errors", violations);

        return ResponseEntity.badRequest().body(response);
    }

}
