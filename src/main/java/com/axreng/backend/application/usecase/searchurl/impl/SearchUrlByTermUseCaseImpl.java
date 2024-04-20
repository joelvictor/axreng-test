package com.axreng.backend.application.usecase.searchurl.impl;

import com.axreng.backend.api.controller.request.TermRequest;
import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.application.idgenerator.RandomIdString;
import com.axreng.backend.application.usecase.crawler.impl.CrawlerUseCaseImpl;
import com.axreng.backend.application.usecase.searchurl.SearchUrlByTermUseCase;
import com.axreng.backend.application.validator.SearchTermValidation;
import com.axreng.backend.config.DomainConfig;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.StatusTerm;
import com.axreng.backend.domain.term.Term;

import java.security.SecureRandom;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SearchUrlByTermUseCaseImpl implements SearchUrlByTermUseCase {

    private final SearchTermValidation searchTermValidation;
    private final TermGateway termGateway;
    private final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);
    private final DomainConfig domainConfig;

    public SearchUrlByTermUseCaseImpl(SearchTermValidation searchTermValidation, TermGateway termGateway,
                                      DomainConfig domainConfig) {
        this.searchTermValidation = searchTermValidation;
        this.termGateway = termGateway;
        this.domainConfig = domainConfig;
    }

    @Override
    public TermResponse search(TermRequest termRequest) throws EmptyKeywordException, InvalidKeywordSizeException {
        Term term = new Term(termRequest);
        searchTermValidation.validate(term);
        term.setId(new RandomIdString(8, new SecureRandom()).nextString());
        term.setStatus(StatusTerm.ACTIVE);
        termGateway.save(term);
        threadPool.submit(new CrawlerUseCaseImpl(termGateway, domainConfig, term));
        return new TermResponse(term.getId());
    }
}
