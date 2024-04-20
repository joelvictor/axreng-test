package com.axreng.backend.application.validator;

import com.axreng.backend.domain.exception.EmptyTermIdException;
import com.axreng.backend.domain.exception.InvalidTermIdSizeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListTermValidationTest {

    private final ListTermValidation validator = new ListTermValidation();

    @Test
    void shouldThrowEmptyTermIdExceptionForNullId() {
        EmptyTermIdException exception = assertThrows(EmptyTermIdException.class, () -> validator.validateId(null));

        assertEquals("Insert a valid id.", exception.getMessage());
    }

    @Test
    void shouldThrowEmptyTermIdExceptionForEmptyId() {
        EmptyTermIdException exception = assertThrows(EmptyTermIdException.class, () -> validator.validateId(""));

        assertEquals("Insert a valid id.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidTermIdSizeExceptionForTooShortId() {
        InvalidTermIdSizeException exception = assertThrows(InvalidTermIdSizeException.class, () -> validator.validateId("abc123"));

        assertEquals("Term id size should be 8 characters.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidTermIdSizeExceptionForTooLongId() {
        InvalidTermIdSizeException exception = assertThrows(InvalidTermIdSizeException.class, () -> validator.validateId("abcdefghi"));

        assertEquals("Term id size should be 8 characters.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionForValidId() throws EmptyTermIdException, InvalidTermIdSizeException {
        validator.validateId("abcd1234");
    }
}
