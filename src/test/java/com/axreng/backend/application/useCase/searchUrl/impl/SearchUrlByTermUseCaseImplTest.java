package com.axreng.backend.application.useCase.searchUrl.impl;

import com.axreng.backend.api.controller.request.TermResponse;
import com.axreng.backend.application.validator.SearchTermValidation;
import com.axreng.backend.domain.exception.BusinessException;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.Term;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class SearchUrlByTermUseCaseImplTest {

    @Mock
    TermGateway termGateway;
    @Mock
    SearchTermValidation searchTermValidation;
    @InjectMocks
    SearchUrlByTermUseCaseImpl searchUrlByTermUseCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearch() throws EmptyKeywordException, InvalidKeywordSizeException {
        when(termGateway.save(any(Term.class))).thenReturn(new Term("keyword"));

        TermResponse result = searchUrlByTermUseCaseImpl.search("keyword");
        assertEquals(new TermResponse("123"), result);
    }

    @Test
    public void testSearchEmptyKeyword() throws EmptyKeywordException, InvalidKeywordSizeException {
        String invalidKeyword = "";
        Mockito.doThrow(EmptyKeywordException.class).when(searchTermValidation).validate(Mockito.any(Term.class));
        BusinessException thrown = assertThrows(
                EmptyKeywordException.class,
                () -> {
                    searchUrlByTermUseCaseImpl.search(invalidKeyword);
                    }
        );
        assertEquals(thrown.getClass(), EmptyKeywordException.class);
    }

    @Test
    public void testSearchInvalidSizeKeyword() throws EmptyKeywordException, InvalidKeywordSizeException {
        String invalidKeyword = "xyz";
        Mockito.doThrow(InvalidKeywordSizeException.class).when(searchTermValidation).validate(Mockito.any(Term.class));
        BusinessException thrown = assertThrows(
                InvalidKeywordSizeException.class,
                () -> {
                    searchUrlByTermUseCaseImpl.search(invalidKeyword);
                    }
        );
        assertEquals(thrown.getClass(), InvalidKeywordSizeException.class);
    }
}