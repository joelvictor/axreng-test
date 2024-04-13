package com.axreng.backend.api.controller;

import com.axreng.backend.application.useCase.searchUrl.SearchUrlByTermUseCase;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchUrlByTermController implements Route {

    private SearchUrlByTermUseCase searchUrlByTermUseCase;

    public SearchUrlByTermController(SearchUrlByTermUseCase searchUrlByTermUseCase) {
        this.searchUrlByTermUseCase = searchUrlByTermUseCase;
    }

    public Object handle(Request request, Response response) throws Exception {
        searchUrlByTermUseCase.search("teste");
        return null;
    }

}
