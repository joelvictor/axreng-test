package com.axreng.backend.application.useCase.listurl.impl;

import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.application.useCase.listurl.ListUrlByTermUseCase;
import com.axreng.backend.application.validator.ListTermValidation;
import com.axreng.backend.domain.exception.EmptyTermIdException;
import com.axreng.backend.domain.exception.InvalidTermIdSizeException;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.infrastructure.dataproviders.TermDataProvider;

public class ListUrlByTermUseCaseImpl implements ListUrlByTermUseCase {

    private ListTermValidation listTermValidation;
    private TermGateway termGateway;

    public ListUrlByTermUseCaseImpl() {
        this.listTermValidation = new ListTermValidation();
        this.termGateway = new TermDataProvider();
    }

    public TermResponse listUrlByTerm(String id) throws EmptyTermIdException, InvalidTermIdSizeException {
        listTermValidation.validateId(id);
//        return termGateway.getTermById(id);
        return null;
    }

}
