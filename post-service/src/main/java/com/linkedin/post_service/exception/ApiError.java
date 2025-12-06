package com.linkedin.post_service.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> subErrors;

    public static ApiError of(HttpStatus s, String msg, String path, List<String> sub) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(s.value())
                .error(s.getReasonPhrase())
                .message(msg)
                .path(path)
                .subErrors(sub)
                .build();
    }
}
