package com.axreng.backend.domain.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException() {
        super("not-found");
    }
}