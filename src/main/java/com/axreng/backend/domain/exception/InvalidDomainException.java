package com.axreng.backend.domain.exception;

public class InvalidDomainException extends BusinessRuntimeException{

    public InvalidDomainException() {
        super("invalid-domain");
    }
}