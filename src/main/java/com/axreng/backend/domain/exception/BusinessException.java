package com.axreng.backend.domain.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class BusinessException extends Exception {

    private String errorCode;

    protected BusinessException(String errorCode) {
        super(getErrorMessage(errorCode));
        this.errorCode = errorCode;
    }

    protected BusinessException(String errorCode, Object... args) {
        super(getErrorMessage(errorCode, args));
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    private static String getErrorMessage(String errorCode, Object... args) {
        String messagePattern = ResourceBundle.getBundle("error_messages", Locale.getDefault())
                .getString(errorCode);
        return MessageFormat.format(messagePattern, args);
    }
}
