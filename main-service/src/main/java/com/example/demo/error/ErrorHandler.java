package com.example.demo.error;

import com.example.demo.error.exception.AccessException;
import com.example.demo.error.exception.BadRequestException;
import com.example.demo.error.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(AccessException.class)
    public ResponseEntity<ApiError> handleAccessException(final AccessException ax) {
        ApiError response = ApiError.builder()
                .status(String.valueOf(HttpStatus.FORBIDDEN))
                .reason("The action is not available")
                .message(ax.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(final BadRequestException be) {
        ApiError response = ApiError.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .reason("Request is incorrect.")
                .message(be.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(final NotFoundException nfe) {
        ApiError response = ApiError.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .reason("Request is incorrect.")
                .message(nfe.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
