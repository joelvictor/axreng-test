package com.axreng.backend.application.useCase.searchUrl.impl;

import com.axreng.backend.api.controller.request.TermResponse;
import com.axreng.backend.application.useCase.searchUrl.SearchUrlByTermUseCase;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.Term;

public class SearchUrlByTermUseCaseImpl implements SearchUrlByTermUseCase {

    private TermGateway termGateway;

    @Override
    public TermResponse search(String keyword) {
        Term term = new Term(keyword);
        term = termGateway.save(term);
        return null;
    }
}
