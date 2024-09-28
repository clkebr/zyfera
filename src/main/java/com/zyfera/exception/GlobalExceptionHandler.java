package com.zyfera.exception;

import com.zyfera.entity.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<ResponseWrapper> serviceException(StudentNotFoundException se) {
    String message = se.getMessage();
    return new ResponseEntity<>(
        ResponseWrapper.builder()
            .success(false)
            .code(HttpStatus.NOT_FOUND.value())
            .message(message)
            .build(),
        HttpStatus.NOT_FOUND);
  }
}
