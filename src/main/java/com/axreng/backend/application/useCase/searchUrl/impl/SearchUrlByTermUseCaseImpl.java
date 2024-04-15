package com.axreng.backend.application.useCase.searchUrl.impl;

import com.axreng.backend.api.controller.request.TermResponse;
import com.axreng.backend.application.idgenerator.RandomIdString;
import com.axreng.backend.application.useCase.crawler.CrawlerUseCase;
import com.axreng.backend.application.useCase.searchUrl.SearchUrlByTermUseCase;
import com.axreng.backend.application.validator.SearchTermValidation;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.Term;
import com.axreng.backend.infrastructure.dataproviders.TermDataProvider;

import java.security.SecureRandom;

public class SearchUrlByTermUseCaseImpl implements SearchUrlByTermUseCase {

    private SearchTermValidation searchTermValidation;
    private TermGateway termGateway;
    private CrawlerUseCase webSiteCrawlerUseCase;

    public SearchUrlByTermUseCaseImpl() {
        this.searchTermValidation = new SearchTermValidation();
        this.termGateway = new TermDataProvider();
    }

    @Override
    public TermResponse search(String keyword) throws EmptyKeywordException, InvalidKeywordSizeException {
        Term term = new Term(keyword);
        term.setId(new RandomIdString(8, new SecureRandom()).nextString());
        searchTermValidation.validate(term);
        termGateway.save(term);
        return null;
    }
}
