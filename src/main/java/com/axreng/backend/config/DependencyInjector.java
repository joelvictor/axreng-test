package com.axreng.backend.config;

import com.axreng.backend.api.controller.ListUrlByTermController;
import com.axreng.backend.api.controller.SearchUrlByTermController;
import com.axreng.backend.application.usecase.listurl.impl.ListUrlByTermUseCaseImpl;
import com.axreng.backend.application.usecase.searchurl.SearchUrlByTermUseCase;
import com.axreng.backend.application.usecase.searchurl.impl.SearchUrlByTermUseCaseImpl;
import com.axreng.backend.application.validator.ListTermValidation;
import com.axreng.backend.application.validator.SearchTermValidation;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.infrastructure.dataproviders.TermDataProvider;
import com.google.gson.Gson;

public class DependencyInjector {

    private final ListUrlByTermController listUrlByTermController;
    private final SearchUrlByTermController searchUrlByTermController;
    private final Gson gson;

    public DependencyInjector() {
        gson = new Gson();
        listUrlByTermController = injectListUrlByTermController();
        searchUrlByTermController = injectSearchUrlByTermController();
    }

    private SearchUrlByTermController injectSearchUrlByTermController() {
        SearchTermValidation searchTermValidation = new SearchTermValidation();
        TermGateway termGateway = new TermDataProvider();
        SearchUrlByTermUseCase searchUrlByTermUseCase = new SearchUrlByTermUseCaseImpl(searchTermValidation, termGateway, new DomainConfig());
        return new SearchUrlByTermController(searchUrlByTermUseCase, gson);
    }

    private ListUrlByTermController injectListUrlByTermController() {
        ListTermValidation listTermValidation = new ListTermValidation();
        TermGateway termGateway = new TermDataProvider();
        ListUrlByTermUseCaseImpl listUrlByTermUseCase = new ListUrlByTermUseCaseImpl(listTermValidation, termGateway);
        return new ListUrlByTermController(listUrlByTermUseCase, gson);
    }

    public ListUrlByTermController getListUrlByTermController() {
        return listUrlByTermController;
    }

    public SearchUrlByTermController getSearchUrlByTermController() {
        return searchUrlByTermController;
    }

}
