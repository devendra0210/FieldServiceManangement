package com.fieldservicemanagement.field_service_management.exception;

public class InvalidTokenTypeException extends RuntimeException{
    public InvalidTokenTypeException(String message) {
        super(message);
    }
}
