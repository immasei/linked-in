package com.linkedin.user_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> respond(HttpStatus status, String msg,
                                             HttpServletRequest req, List<String> sub) {
        return ResponseEntity.status(status)
                .body(ApiError.of(status, msg, req.getRequestURI(), sub));
    }

    // 404 – resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return respond(NOT_FOUND, ex.getMessage(), req, null);
    }

    // 400 – explicit bad requests we throw in code
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return respond(BAD_REQUEST, ex.getMessage(), req, null);
    }

    // 409 – DB unique/constraint violations
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex,
                                                        HttpServletRequest req) {
        String mostSpecific = ex.getMostSpecificCause() == null
                ? null
                : ex.getMostSpecificCause().getMessage();

        return respond(HttpStatus.CONFLICT, "Constraint violation", req,
                mostSpecific == null ? null : List.of(mostSpecific));
    }

    // 400 – DTO @Valid errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex,
                                                     HttpServletRequest req) {
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());

        return respond(HttpStatus.BAD_REQUEST, "Validation failed", req, details);
    }

    // 500 – fallback handler
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex, HttpServletRequest req) {
        return respond(INTERNAL_SERVER_ERROR, "Something went wrong", req, List.of(ex.getMessage()));
    }

}
