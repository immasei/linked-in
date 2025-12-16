package com.linkedin.connection_service.exception;

import jakarta.servlet.http.HttpServletRequest;
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
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        return respond(NOT_FOUND, ex.getMessage(), req, null);
    }

    // 400 – explicit bad requests we throw in code
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return respond(BAD_REQUEST, ex.getMessage(), req, null);
    }

    // 409 - idempotent
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex, HttpServletRequest req) {
        return respond(CONFLICT, ex.getMessage(), req, null);
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