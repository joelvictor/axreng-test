package com.axreng.backend.application.usecase.listurl.impl;

import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.application.usecase.listurl.ListUrlByTermUseCase;
import com.axreng.backend.application.validator.ListTermValidation;
import com.axreng.backend.domain.exception.EmptyTermIdException;
import com.axreng.backend.domain.exception.InvalidTermIdSizeException;
import com.axreng.backend.domain.exception.NotFoundException;
import com.axreng.backend.domain.gateways.TermGateway;

public class ListUrlByTermUseCaseImpl implements ListUrlByTermUseCase {

    private final ListTermValidation listTermValidation;
    private final TermGateway termGateway;

    public ListUrlByTermUseCaseImpl(ListTermValidation listTermValidation, TermGateway termGateway) {
        this.listTermValidation = listTermValidation;
        this.termGateway = termGateway;
    }

    @Override
    public TermResponse listUrlByTerm(String id) throws EmptyTermIdException, InvalidTermIdSizeException, NotFoundException {
        listTermValidation.validateId(id);
        return new TermResponse(termGateway.findTermById(id));
    }

}
