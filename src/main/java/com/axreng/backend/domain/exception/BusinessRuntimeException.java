package com.axreng.backend.domain.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class BusinessRuntimeException extends RuntimeException {

    private final String errorCode;

    protected BusinessRuntimeException(String errorCode) {
        super(getErrorMessage(errorCode));
        this.errorCode = errorCode;
    }

    protected BusinessRuntimeException(String errorCode, Object... args) {
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
