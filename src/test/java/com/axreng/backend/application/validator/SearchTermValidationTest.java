package com.axreng.backend.application.validator;

import com.axreng.backend.api.controller.request.TermRequest;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import com.axreng.backend.domain.term.Term;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchTermValidationTest {

    private SearchTermValidation validator = new SearchTermValidation();

    @Test
    void shouldThrowEmptyKeywordExceptionForNullKeyword() {
        Term term = new Term();

        EmptyKeywordException exception = assertThrows(EmptyKeywordException.class, () -> {
            validator.validate(term);
        });

        assertEquals("Insert a valid keyword.", exception.getMessage());
    }

    @Test
    void shouldThrowEmptyKeywordExceptionForEmptyKeyword() {
        Term term = new Term();

        EmptyKeywordException exception = assertThrows(EmptyKeywordException.class, () -> {
            validator.validate(term);
        });

        assertEquals("Insert a valid keyword.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidKeywordSizeExceptionForTooShortKeyword() {
        Term term = new Term(new TermRequest("abc"));

        InvalidKeywordSizeException exception = assertThrows(InvalidKeywordSizeException.class, () -> {
            validator.validate(term);
        });

        assertEquals("Keyword size should be greater than 3 and lesser than 32.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidKeywordSizeExceptionForTooLongKeyword() {
        Term term = new Term(new TermRequest("thiskeywordthiswaytoolongtobevalid"));

        InvalidKeywordSizeException exception = assertThrows(InvalidKeywordSizeException.class, () -> {
            validator.validate(term);
        });

        assertEquals("Keyword size should be greater than 3 and lesser than 32.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionForValidKeyword() throws EmptyKeywordException, InvalidKeywordSizeException {
        Term term = new Term(new TermRequest("validKeyword"));
        validator.validate(term);
    }
}
