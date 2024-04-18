package com.axreng.backend.application.useCase.searchUrl.impl;

import com.axreng.backend.api.controller.request.TermRequest;
import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.application.idgenerator.RandomIdString;
import com.axreng.backend.application.useCase.crawler.CrawlerUseCase;
import com.axreng.backend.application.useCase.crawler.impl.CrawlerUseCaseImpl;
import com.axreng.backend.application.useCase.searchUrl.SearchUrlByTermUseCase;
import com.axreng.backend.application.validator.SearchTermValidation;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.StatusTerm;
import com.axreng.backend.domain.term.Term;
import com.axreng.backend.infrastructure.dataproviders.TermDataProvider;

import java.security.SecureRandom;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SearchUrlByTermUseCaseImpl implements SearchUrlByTermUseCase {

    private SearchTermValidation searchTermValidation;
    private TermGateway termGateway;
    private CrawlerUseCase crawlerUseCase;
    private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);

    public SearchUrlByTermUseCaseImpl() {
        this.searchTermValidation = new SearchTermValidation();
        this.termGateway = new TermDataProvider();
    }

    @Override
    public TermResponse search(TermRequest termRequest) throws EmptyKeywordException, InvalidKeywordSizeException {
        Term term = new Term(termRequest);
        searchTermValidation.validate(term);
        term.setId(new RandomIdString(8, new SecureRandom()).nextString());
        term.setStatus(StatusTerm.ACTIVE);
        termGateway.save(term);
        threadPool.submit(this.crawlerUseCase = new CrawlerUseCaseImpl(term));
        return new TermResponse(term);
    }
}
