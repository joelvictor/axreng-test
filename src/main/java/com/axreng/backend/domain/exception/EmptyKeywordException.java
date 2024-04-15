package com.axreng.backend.domain.exception;

public class EmptyKeywordException extends BusinessException {

    public EmptyKeywordException() {
        super("empty-keyword");
    }
}