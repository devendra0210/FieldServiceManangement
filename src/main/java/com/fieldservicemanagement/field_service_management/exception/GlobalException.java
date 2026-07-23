package com.fieldservicemanagement.field_service_management.exception;


import com.fieldservicemanagement.field_service_management.common.response.ErrorResponse;
import com.fieldservicemanagement.field_service_management.common.response.Errors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;


@RestControllerAdvice
public class GlobalException  {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException e){
        return ResponseEntity.status(404).body(
                ErrorResponse.builder()
                        .success(false)
                        .status(404)
                        .message(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .errors(null)
                        .build()
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException e){
        return ResponseEntity.status(401).body(
                ErrorResponse.builder()
                        .success(false)
                        .status(401)
                        .message(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .errors(null)
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex)
    {
        ArrayList<Errors> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> {
                    errors.add(
                            new Errors(
                                    e.getField(),
                                    e.getDefaultMessage()
                            )
                    );
                });
        return ResponseEntity.status(400).body(
                ErrorResponse.builder()
                        .success(false)
                        .status(400)
                        .message("Fields filled in incorrectly")
                        .timestamp(LocalDateTime.now())
                        .errors(errors)
                        .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeny(AccessDeniedException e){
        return ResponseEntity.status(403).body(
                ErrorResponse.builder()
                        .success(false)
                        .status(403)
                        .message(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleInvalidJwt(JwtException ex) {
        return ResponseEntity.status(401).body(
                ErrorResponse.builder()
                        .success(false)
                        .status(401)
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
