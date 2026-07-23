package com.fieldservicemanagement.field_service_management.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Incorrect Email or Password");
    }
}
