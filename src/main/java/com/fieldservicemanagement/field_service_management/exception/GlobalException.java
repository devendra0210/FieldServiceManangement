package com.fieldservicemanagement.field_service_management.exception;


import com.fieldservicemanagement.field_service_management.common.response.ErrorResponse;
import com.fieldservicemanagement.field_service_management.common.response.Errors;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalException  {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(CustomNotFoundException e){
        return ResponseEntity.status(404).body(errorResponse(404, e.getMessage(), null));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException e){
        return ResponseEntity.status(401).body(errorResponse(401, e.getMessage(), null));
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
        return ResponseEntity.status(400).body(errorResponse(400, "Fields filled in incorrectly", errors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeny(AccessDeniedException e){
        return ResponseEntity.status(403).body(errorResponse(403, e.getMessage(), null));
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleInvalidJwt(JwtException ex) {
        return ResponseEntity.status(401).body(errorResponse(401, ex.getMessage(), null));
    }

    @ExceptionHandler(InvalidTokenTypeException.class)
    public ResponseEntity<?> handleInvalidToken(InvalidTokenTypeException ex) {
        return ResponseEntity.status(401).body(errorResponse(401, ex.getMessage(), null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(400).body(errorResponse(
                400,
                "Role must be one of: ROLE_DISPATCHER, ROLE_TECHNICAL_SPECIALIST, ROLE_ADMIN, ROLE_CUSTOMER, ROLE_MANAGER",
                null)
        );
    }

    public ErrorResponse errorResponse(int status, String message, List<Errors> errors) {
        return ErrorResponse.builder()
                .success(false)
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }
}
