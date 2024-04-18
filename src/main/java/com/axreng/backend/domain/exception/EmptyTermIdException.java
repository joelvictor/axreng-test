package com.axreng.backend.domain.exception;

public class EmptyTermIdException extends BusinessException {

    public EmptyTermIdException() {
        super("empty-term-id");
    }
}