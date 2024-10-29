package com.fiserv.clienttest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.net.ssl.SSLHandshakeException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class SSLHandshakeExceptionHandler {

    @ExceptionHandler(SSLHandshakeException.class)
    protected ResponseEntity<ExceptionResponse> handleSSLHandshakeException(SSLHandshakeException ex,
                                                                            HttpServletRequest request) {
        return ResponseEntity.badRequest().body(ExceptionResponse.builder()
                .code(400)
                .status("Bad Certificate")
                .timestamp(LocalDateTime.now())
                .messages(ex.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .build());
    }
}
