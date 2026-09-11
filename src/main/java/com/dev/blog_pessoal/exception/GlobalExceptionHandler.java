package com.dev.blog_pessoal.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<CustomErrorDTO> hadleResourceNotFound(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
    }
}
