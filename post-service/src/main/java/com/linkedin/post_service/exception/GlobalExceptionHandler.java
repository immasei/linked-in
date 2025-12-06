package com.linkedin.post_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> respond(HttpStatus status, String msg,
                                             HttpServletRequest req, List<String> sub) {
        return ResponseEntity.status(status)
                .body(ApiError.of(status, msg, req.getRequestURI(), sub));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return respond(NOT_FOUND, ex.getMessage(), req, null);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> badRequest(BadRequestException ex, HttpServletRequest req) {
        return respond(BAD_REQUEST, ex.getMessage(), req, null);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> runtime(Exception ex, HttpServletRequest req) {
        return respond(INTERNAL_SERVER_ERROR, "Something went wrong", req, List.of(ex.getMessage()));
    }

}
