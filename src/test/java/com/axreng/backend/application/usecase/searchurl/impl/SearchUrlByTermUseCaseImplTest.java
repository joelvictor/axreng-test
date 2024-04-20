package com.axreng.backend.application.usecase.searchurl.impl;

import com.axreng.backend.api.controller.request.TermRequest;
import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.application.validator.SearchTermValidation;
import com.axreng.backend.config.DomainConfig;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchUrlByTermUseCaseImplTest {

    @Mock
    TermGateway termGateway;
    @Mock
    SearchTermValidation searchTermValidation;
    @Mock
    DomainConfig domainConfig;
    @InjectMocks
    SearchUrlByTermUseCaseImpl searchUrlByTermUseCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(domainConfig.getBaseUrl()).thenReturn("http://www.example.com/");
    }

    @Test
    void shouldCreateAndSubmitCrawlerTaskWhenInputIsValid() throws EmptyKeywordException, InvalidKeywordSizeException {
        TermRequest termRequest = new TermRequest("validKeyword");
        when(termGateway.save(any(Term.class))).thenReturn(any());
        TermResponse result = searchUrlByTermUseCaseImpl.search(termRequest);
        verify(termGateway).save(any(Term.class));
        assertNotNull(result);
    }

    @Test
    void testSearchEmptyKeyword() throws EmptyKeywordException, InvalidKeywordSizeException {
        String invalidKeyword = "";
        Mockito.doThrow(EmptyKeywordException.class).when(searchTermValidation).validate(Mockito.any(Term.class));
        BusinessException thrown = assertThrows(
                EmptyKeywordException.class,
                () -> searchUrlByTermUseCaseImpl.search(new TermRequest(invalidKeyword))
        );
        assertEquals(thrown.getClass(), EmptyKeywordException.class);
    }

    @Test
    void testSearchInvalidSizeKeyword() throws EmptyKeywordException, InvalidKeywordSizeException {
        String invalidKeyword = "xyz";
        Mockito.doThrow(InvalidKeywordSizeException.class).when(searchTermValidation).validate(Mockito.any(Term.class));
        BusinessException thrown = assertThrows(
                InvalidKeywordSizeException.class,
                () -> searchUrlByTermUseCaseImpl.search(new TermRequest(invalidKeyword))
        );
        assertEquals(thrown.getClass(), InvalidKeywordSizeException.class);
    }
}