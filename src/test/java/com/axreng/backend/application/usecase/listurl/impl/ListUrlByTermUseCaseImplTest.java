package com.axreng.backend.application.usecase.listurl.impl;

import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.application.validator.ListTermValidation;
import com.axreng.backend.domain.exception.BusinessException;
import com.axreng.backend.domain.exception.EmptyTermIdException;
import com.axreng.backend.domain.exception.InvalidTermIdSizeException;
import com.axreng.backend.domain.exception.NotFoundException;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.Term;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ListUrlByTermUseCaseImplTest {

    @Mock
    private TermGateway termGateway;

    @Mock
    private ListTermValidation listTermValidation;

    @InjectMocks
    private ListUrlByTermUseCaseImpl listUrlByTermUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTermResponseWhenIdIsValidAndTermExists() throws EmptyTermIdException, InvalidTermIdSizeException, NotFoundException {
        String termId = "validId1";
        Term expectedTerm = new Term();
        expectedTerm.setId(termId);

        when(termGateway.findTermById(termId)).thenReturn(expectedTerm);

        TermResponse result = listUrlByTermUseCase.listUrlByTerm(termId);

        assertNotNull(result);
        assertEquals(termId, result.getId());
    }

    @Test
    void shouldThrowEmptyTermIdExceptionWhenIdIsNull() throws EmptyTermIdException, InvalidTermIdSizeException {
        Mockito.doThrow(EmptyTermIdException.class).when(listTermValidation).validateId(null);
        BusinessException thrown = assertThrows(
                EmptyTermIdException.class,
                () -> listUrlByTermUseCase.listUrlByTerm(null));
        assertEquals(thrown.getClass(), EmptyTermIdException.class);
    }

    @Test
    void shouldThrowInvalidTermIdSizeExceptionWhenIdIsInvalidSize() throws EmptyTermIdException, InvalidTermIdSizeException {
        String keyword = "sm";
        Mockito.doThrow(InvalidTermIdSizeException.class).when(listTermValidation).validateId(keyword);
        BusinessException thrown = assertThrows(InvalidTermIdSizeException.class, () ->
                listUrlByTermUseCase.listUrlByTerm(keyword));
        assertEquals(thrown.getClass(), InvalidTermIdSizeException.class);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenTermIsNotFound() throws NotFoundException {
        String termId = "validId1";
        when(termGateway.findTermById(termId)).thenThrow(new NotFoundException());

        assertThrows(NotFoundException.class, () ->
                listUrlByTermUseCase.listUrlByTerm(termId));
    }
}
