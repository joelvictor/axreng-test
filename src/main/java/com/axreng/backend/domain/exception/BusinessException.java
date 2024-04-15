package com.axreng.backend.domain.exception;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
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
        if (messagePattern != null) {
            return MessageFormat.format(messagePattern, args);
        } else {
            return "Unknown business error: " + errorCode;
        }
    }
}
